package com.ronaksoftware.musicchi.ui.components;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Handler;
import android.view.TextureView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.UserConfigs;
import com.ronaksoftware.musicchi.controllers.MediaController;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

@SuppressLint("NewApi")
public class Player implements ExoPlayer.EventListener, SimpleExoPlayer.VideoListener {

    public interface RendererBuilder {
        void buildRenderers(Player player);

        void cancel();
    }

    public interface VideoPlayerDelegate {
        void onStateChanged(boolean playWhenReady, int playbackState);

        void onError(Exception e);

        void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio);

        void onRenderedFirstFrame();

        void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture);

        boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture);
    }

    private SimpleExoPlayer player;
    private MappingTrackSelector trackSelector;
    private Handler mainHandler;
    private DataSource.Factory mediaDataSourceFactory;
    private TextureView textureView;
    private boolean isStreaming;
    private boolean autoplay;

    private boolean videoPlayerReady;

    private VideoPlayerDelegate delegate;
    private int lastReportedPlaybackState;
    private boolean lastReportedPlayWhenReady;

    private static final int RENDERER_BUILDING_STATE_IDLE = 1;
    private static final int RENDERER_BUILDING_STATE_BUILDING = 2;
    private static final int RENDERER_BUILDING_STATE_BUILT = 3;

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private CompositeDisposable disposables = new CompositeDisposable();

    public Player() {
        DefaultHttpDataSourceFactory dataSource = new DefaultHttpDataSourceFactory("Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)", BANDWIDTH_METER);
        dataSource.getDefaultRequestProperties().set("AccessKey","8ynNr1zPWYEnRJEigKS3VKeUR7ptIpBQxkaP2mOhKBthGfpOTahq0skqeMHI4lUE");
        dataSource.getDefaultRequestProperties().set("SessionID", UserConfigs.sessionID);
        mediaDataSourceFactory = dataSource;

        mainHandler = new Handler();

        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        lastReportedPlaybackState = ExoPlayer.STATE_IDLE;

        disposables.add(MediaController.playerDidStartPlaying.observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object[]>() {
            @Override
            public void accept(Object[] objects) throws Exception {
                Player p = (Player) objects[0];
                if (p != Player.this && isPlaying()) {
                    pause();
                }
            }
        }));
    }

    private void ensurePleyaerCreated() {
        DefaultLoadControl loadControl = new DefaultLoadControl(
                new DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE),
                DefaultLoadControl.DEFAULT_MIN_BUFFER_MS,
                DefaultLoadControl.DEFAULT_MAX_BUFFER_MS,
                100,
                DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS,
                DefaultLoadControl.DEFAULT_TARGET_BUFFER_BYTES,
                DefaultLoadControl.DEFAULT_PRIORITIZE_TIME_OVER_SIZE_THRESHOLDS);

        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(ApplicationLoader.applicationContext, trackSelector, loadControl, null, DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER);
            player.addListener(this);
            player.setVideoListener(this);
            player.setVideoTextureView(textureView);
            player.setPlayWhenReady(autoplay);
        }
    }

    public void preparePlayer(Uri uri, String type) {
        videoPlayerReady = false;
        String scheme = uri.getScheme();
        isStreaming = scheme != null && !scheme.startsWith("file");
        ensurePleyaerCreated();
        MediaSource mediaSource;
        switch (type) {
            case "dash":
                mediaSource = new DashMediaSource(uri, mediaDataSourceFactory, new DefaultDashChunkSource.Factory(mediaDataSourceFactory), mainHandler, null);
                break;
//            case "hls":
//                mediaSource = new HlsMediaSource(uri, mediaDataSourceFactory, mainHandler, null);
//                break;
            case "ss":
                mediaSource = new SsMediaSource(uri, mediaDataSourceFactory, new DefaultSsChunkSource.Factory(mediaDataSourceFactory), mainHandler, null);
                break;
            default:
                mediaSource = new ExtractorMediaSource(uri, mediaDataSourceFactory, new DefaultExtractorsFactory(), mainHandler, null);
                break;
        }
        player.prepare(mediaSource, true, true);
    }

    public boolean isPlayerPrepared() {
        return player != null;
    }

    public void releasePlayer(boolean async) {
        if (player != null) {
            player.release();
            player = null;
        }

        disposables.dispose();
    }

    public void setTextureView(TextureView texture) {
        if (textureView == texture) {
            return;
        }
        textureView = texture;
        if (player == null) {
            return;
        }
        player.setVideoTextureView(textureView);
    }

    public void play() {
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }

    public void pause() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    public void setPlaybackSpeed(float speed) {
        if (player != null) {
            player.setPlaybackParameters(new PlaybackParameters(speed, speed > 1.0f ? 0.98f : 1.0f));
        }
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        autoplay = playWhenReady;
        if (player != null) {
            player.setPlayWhenReady(playWhenReady);
        }
    }

    public long getDuration() {
        return player != null ? player.getDuration() : 0;
    }

    public long getCurrentPosition() {
        return player != null ? player.getCurrentPosition() : 0;
    }

    public boolean isMuted() {
        return player.getVolume() == 0.0f;
    }

    public void setMute(boolean value) {
        if (player != null) {
            player.setVolume(value ? 0.0f : 1.0f);
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onSurfaceSizeChanged(int width, int height) {

    }

    public void setVolume(float volume) {
        if (player != null) {
            player.setVolume(volume);
        }
    }

    public void seekTo(long positionMs) {
        if (player != null) {
            player.seekTo(positionMs);
        }
    }

    public void setDelegate(VideoPlayerDelegate videoPlayerDelegate) {
        delegate = videoPlayerDelegate;
    }

    public int getBufferedPercentage() {
        return isStreaming ? (player != null ? player.getBufferedPercentage() : 0) : 100;
    }

    public long getBufferedPosition() {
        return player != null ? (isStreaming ? player.getBufferedPosition() : player.getDuration()) : 0;
    }

    public boolean isStreaming() {
        return isStreaming;
    }

    public boolean isPlaying() {
        return player != null && player.getPlayWhenReady();
    }

    public boolean isBuffering() {
        return player != null && lastReportedPlaybackState == ExoPlayer.STATE_BUFFERING;
    }

    public void setStreamType(int type) {
        if (player != null) {
            player.setAudioStreamType(type);
        }
    }

    private void checkPlayersReady() {
        if (videoPlayerReady) {
            play();
        }
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        maybeReportPlayerState();
        if (playWhenReady && playbackState == com.google.android.exoplayer2.Player.STATE_READY) {
            MediaController.playerDidStartPlaying.onNext(new Object[]{this});
        }
        if (!videoPlayerReady && playbackState == com.google.android.exoplayer2.Player.STATE_READY) {
            videoPlayerReady = true;
            checkPlayersReady();
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        delegate.onError(error);
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        delegate.onVideoSizeChanged(width, height, unappliedRotationDegrees, pixelWidthHeightRatio);
    }

    @Override
    public void onRenderedFirstFrame() {
        delegate.onRenderedFirstFrame();
    }

//    @Override
//    public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
//        return delegate.onSurfaceDestroyed(surfaceTexture);
//    }
//
//    @Override
//    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
//        delegate.onSurfaceTextureUpdated(surfaceTexture);
//    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    private void maybeReportPlayerState() {
        if (player == null) {
            return;
        }

        boolean playWhenReady = player.getPlayWhenReady();
        int playbackState = player.getPlaybackState();
        if (lastReportedPlayWhenReady != playWhenReady || lastReportedPlaybackState != playbackState) {
            delegate.onStateChanged(playWhenReady, playbackState);
            lastReportedPlayWhenReady = playWhenReady;
            lastReportedPlaybackState = playbackState;
        }
    }
}

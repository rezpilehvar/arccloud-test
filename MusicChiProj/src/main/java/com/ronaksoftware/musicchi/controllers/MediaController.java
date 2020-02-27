package com.ronaksoftware.musicchi.controllers;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.net.Uri;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.models.Song;
import com.ronaksoftware.musicchi.ui.components.Player;
import com.ronaksoftware.musicchi.utils.Constants;
import com.ronaksoftware.musicchi.utils.Queues;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class MediaController implements AudioManager.OnAudioFocusChangeListener {
    private static volatile MediaController Instance;

    public static MediaController getInstance() {
        MediaController localInstance = Instance;
        if (localInstance == null) {
            synchronized (MediaController.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new MediaController();
                }
            }
        }
        return localInstance;
    }

    public static final Subject<Object[]> playerDidStartPlaying = PublishSubject.create();

    protected static AudioManager audioManager;
    private Player audioPlayer;

    private boolean hasAudioFocus;
    private int audioFocus = Constants.AUDIO_NO_FOCUS_NO_DUCK;
    private boolean resumeAudioOnFocusGain;

    private boolean isPaused = false;
    private Song playingSong;
    private boolean downloadingCurrentMessage;
    private float seekToProgressPending;
    private long lastProgress = 0;

    private Timer progressTimer = null;
    private final Object progressTimerSync = new Object();
    private final Object sync = new Object();

    private MediaController() {
        try {
            audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(Context.AUDIO_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean playSong(Song song) {
        if (song == null) {
            return false;
        }
        if ((audioPlayer != null) && isSamePlayingAudio(song)) {
            if (isPaused) {
                resumeAudio(song);
            }
            return true;
        }

        if (getPlayingSongObject() != null) {
            getPlayingSongObject().resetPlayingProgress();
        }
        cleanupPlayer(true);
        seekToProgressPending = 0;

        try {
            audioPlayer = new Player();
            audioPlayer.setDelegate(new Player.VideoPlayerDelegate() {
                @Override
                public void onStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState == ExoPlayer.STATE_ENDED) {
                        cleanupPlayer(true);
                    } else if (seekToProgressPending != 0 && (playbackState == ExoPlayer.STATE_READY || playbackState == ExoPlayer.STATE_IDLE)) {
                        int seekTo = (int) (audioPlayer.getDuration() * seekToProgressPending);
                        audioPlayer.seekTo(seekTo);
                        lastProgress = seekTo;
                        seekToProgressPending = 0;
                    }
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }

                @Override
                public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

                }

                @Override
                public void onRenderedFirstFrame() {

                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

                }

                @Override
                public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
                    return false;
                }
            });

            Uri uri = Uri.parse("https://V2.blipapi.xyz/music/download/songs/" + song.getId());
            audioPlayer.preparePlayer(uri, "other");

            audioPlayer.setStreamType(AudioManager.STREAM_MUSIC);
            audioPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
            EventController.songPlayingPlayStateChanged.onNext(new Object[]{playingSong != null ? playingSong.getId() : 0});

            if (audioPlayer != null) {
                audioPlayer.releasePlayer(true);
                audioPlayer = null;
                isPaused = false;
                playingSong = null;
                downloadingCurrentMessage = false;
            }
            return false;
        }

        checkAudioFocus(song);
        setPlayerVolume();

        isPaused = false;
        lastProgress = 0;
        playingSong = song;
        startProgressTimer(playingSong);
        EventController.songPlayingDidStart.onNext(new Object[]{song});

        try {
            if (playingSong.audioProgress != 0) {
                long duration = audioPlayer.getDuration();
//                if (duration == C.TIME_UNSET) {
//                    duration = (long) playingSong.getDuration() * 1000;
//                }
                int seekTo = (int) (duration * playingSong.audioProgress);
                audioPlayer.seekTo(seekTo);
            }
        } catch (Exception e2) {
            playingSong.resetPlayingProgress();
            EventController.songPlayingProgressDidChanged.onNext(new Object[]{playingSong.getId(), 0});
            e2.printStackTrace();
        }

        return true;
    }

    public boolean resumeAudio(Song song) {
        if (audioPlayer == null || song == null || getPlayingSongObject() == null || !isSamePlayingAudio(song)) {
            return false;
        }

        try {
            startProgressTimer(getPlayingSongObject());
            if (audioPlayer != null) {
                audioPlayer.play();
            }
            checkAudioFocus(song);
            isPaused = false;
            EventController.songPlayingPlayStateChanged.onNext(new Object[]{getPlayingSongObject().getId()});
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void checkAudioFocus(Song song) {
        if (!hasAudioFocus) {
            hasAudioFocus = true;
            int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                audioFocus = Constants.AUDIO_FOCUSED;
            }
        }
    }

    private boolean isSamePlayingAudio(Song song) {
        return playingSong != null && playingSong.getId().equals(song.getId());
    }

    public boolean isPlayingSong(Song song) {
        if (audioPlayer == null || song == null || playingSong == null) {
            return false;
        }
        if (isSamePlayingAudio(song)) {
            return !downloadingCurrentMessage;
        }
        return false;
    }

    public Song getPlayingSongObject() {
        return playingSong;
    }

    public boolean isSongPaused() {
        return isPaused || downloadingCurrentMessage;
    }

    public boolean pauseSong(Song song) {
        if (audioPlayer == null || song == null || playingSong == null || !isSamePlayingAudio(song)) {
            return false;
        }
        stopProgressTimer();
        try {
            if (audioPlayer != null) {
                audioPlayer.pause();
            }
            isPaused = true;
            EventController.songPlayingPlayStateChanged.onNext(new Object[]{song.getId()});
        } catch (Exception e) {
            e.printStackTrace();
            isPaused = false;
            return false;
        }
        return true;
    }

    private void stopProgressTimer() {
        synchronized (progressTimerSync) {
            if (progressTimer != null) {
                try {
                    progressTimer.cancel();
                    progressTimer = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startProgressTimer(final Song currentPlayingSongObject) {
        synchronized (progressTimerSync) {
            if (progressTimer != null) {
                try {
                    progressTimer.cancel();
                    progressTimer = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            progressTimer = new Timer();
            progressTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    synchronized (sync) {
                        Queues.runOnUIThread(() -> {
                            if (currentPlayingSongObject != null && audioPlayer != null && !isPaused) {
                                try {
                                    long duration;
                                    long progress;
                                    float value;
                                    float bufferedValue;
                                    duration = audioPlayer.getDuration();
                                    progress = audioPlayer.getCurrentPosition();
                                    value = duration != C.TIME_UNSET && duration >= 0 ? (progress / (float) duration) : 0.0f;
                                    bufferedValue = audioPlayer.getBufferedPosition() / (float) duration;
                                    if (duration == C.TIME_UNSET || progress < 0 || seekToProgressPending != 0) {
                                        return;
                                    }
                                    lastProgress = progress;
                                    getPlayingSongObject().audioPlayerDuration = (int) (duration / 1000);
                                    getPlayingSongObject().audioProgress = value;
                                    getPlayingSongObject().audioProgressSec = (int) (lastProgress / 1000);
                                    getPlayingSongObject().bufferedProgress = bufferedValue;
                                    EventController.songPlayingProgressDidChanged.onNext(new Object[]{getPlayingSongObject().getId(), value});
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }, 0, 17);
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            if (isPlayingSong(getPlayingSongObject()) && !isSongPaused()) {
                pauseSong(getPlayingSongObject());
            }
            hasAudioFocus = false;
            audioFocus = Constants.AUDIO_NO_FOCUS_NO_DUCK;
        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            audioFocus = Constants.AUDIO_FOCUSED;
            if (resumeAudioOnFocusGain) {
                resumeAudioOnFocusGain = false;
                if (isPlayingSong(getPlayingSongObject()) && isSongPaused()) {
                    playSong(getPlayingSongObject());
                }
            }
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
            audioFocus = Constants.AUDIO_NO_FOCUS_CAN_DUCK;
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
            audioFocus = Constants.AUDIO_NO_FOCUS_NO_DUCK;
            if (isPlayingSong(getPlayingSongObject()) && !isSongPaused()) {
                pauseSong(getPlayingSongObject());
                resumeAudioOnFocusGain = true;
            }
        }
        setPlayerVolume();
    }

    private void setPlayerVolume() {
        try {
            float volume;
            if (audioFocus != Constants.AUDIO_NO_FOCUS_CAN_DUCK) {
                volume = Constants.VOLUME_NORMAL;
            } else {
                volume = Constants.VOLUME_DUCK;
            }
            if (audioPlayer != null) {
                audioPlayer.setVolume(volume);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanupPlayer(boolean notify) {
        if (audioPlayer != null) {
            try {
                audioPlayer.releasePlayer(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            audioPlayer = null;
        }
        stopProgressTimer();
        lastProgress = 0;
        isPaused = false;

        if (getPlayingSongObject() != null) {
            Song lastFile = getPlayingSongObject();
            if (notify) {
                getPlayingSongObject().resetPlayingProgress();
                EventController.songPlayingProgressDidChanged.onNext(new Object[]{getPlayingSongObject().getId(), 0.0f});
            }
            playingSong = null;
            downloadingCurrentMessage = false;
            if (notify) {
                audioManager.abandonAudioFocus(this);
                hasAudioFocus = false;

                EventController.songPlayingDidReset.onNext(new Object[]{lastFile.getId()});
            }
        }
    }
}

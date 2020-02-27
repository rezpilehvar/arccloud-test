package com.ronaksoftware.musicchi.ui.cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ronaksoftware.musicchi.R;
import com.ronaksoftware.musicchi.UserConfigs;
import com.ronaksoftware.musicchi.controllers.MediaController;
import com.ronaksoftware.musicchi.models.Song;
import com.ronaksoftware.musicchi.ui.components.CircleImageView;
import com.ronaksoftware.musicchi.ui.components.CircleProgressBar;
import com.ronaksoftware.musicchi.ui.presenter.Theme;
import com.ronaksoftware.musicchi.utils.Assets;
import com.ronaksoftware.musicchi.utils.DisplayUtility;
import com.ronaksoftware.musicchi.utils.LayoutHelper;

public class SongCell extends FrameLayout {
    private TextView contentTextView;
    private CircleImageView coverImageView;
    private CircleProgressBar progressBar;

    private Song currentSongObject;
    private Drawable stopDrawable;

    private Drawable playingBackgroundDrawable;
    private Drawable normalBackgroundDrawable;

    public SongCell(@NonNull Context context) {
        super(context);
        playingBackgroundDrawable = Theme.getRoundRectSelectorDrawable(Color.parseColor("#11151E"), Color.GRAY, DisplayUtility.dp(40));
        normalBackgroundDrawable = Theme.getRoundRectSelectorDrawable(Color.GRAY, Color.parseColor("#11151E"), DisplayUtility.dp(40));
        stopDrawable = context.getResources().getDrawable(R.drawable.baseline_pause_white_24);

        coverImageView = new CircleImageView(context) {

            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);

                if (MediaController.getInstance().isPlayingSong(currentSongObject) && !MediaController.getInstance().isSongPaused()) {
                    Assets.overlayPaint.setColor(Color.BLACK);
                    Assets.overlayPaint.setAlpha(100);
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2, DisplayUtility.dp(20), Assets.overlayPaint);

                    int size = DisplayUtility.dp(24);
                    int top = (getHeight() / 2) - (size / 2);
                    int left = (getWidth() / 2) - (size / 2);
                    stopDrawable.setBounds(left, top, left + size, top + size);
                    stopDrawable.draw(canvas);
                }
            }
        };
        coverImageView.setImageResource(R.drawable.avatar);
        addView(coverImageView, LayoutHelper.createFrame(40, 40, Gravity.CENTER_VERTICAL | Gravity.LEFT, 4, 0, 0, 0));

        progressBar = new CircleProgressBar(context);
        progressBar.setColor(Color.WHITE);
        progressBar.setMax(1);
        progressBar.setMin(0);
        progressBar.setProgress(0);
        progressBar.setStrokeWidth(DisplayUtility.dp(4));
        addView(progressBar, LayoutHelper.createFrame(40, 40, Gravity.CENTER_VERTICAL | Gravity.LEFT, 4, 0, 0, 0));

        contentTextView = new TextView(context);
        contentTextView.setTextColor(Color.WHITE);
        contentTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        addView(contentTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL | Gravity.LEFT, 56, 0, 0, 0));
    }

    public void update(Song song) {
        currentSongObject = song;
        contentTextView.setText(String.format("%s - %s", song.getArtists(), song.getTitle()));
        GlideUrl glideUrl = new GlideUrl("https://V2.blipapi.xyz/music/download/covers/" + song.getId(), new LazyHeaders.Builder()
                .addHeader("AccessKey", "8ynNr1zPWYEnRJEigKS3VKeUR7ptIpBQxkaP2mOhKBthGfpOTahq0skqeMHI4lUE")
                .addHeader("SessionID", UserConfigs.sessionID)
                .build());
        Glide.with(getContext()).load(glideUrl).into(coverImageView);
        updateButtonState();
    }

    public void updateButtonState() {

        if (MediaController.getInstance().isPlayingSong(currentSongObject) && !MediaController.getInstance().isSongPaused() && getBackground() != playingBackgroundDrawable) {
            setBackground(playingBackgroundDrawable);
            if (progressBar.getVisibility() != VISIBLE) {
                progressBar.setVisibility(VISIBLE);
            }
        } else if (getBackground() != normalBackgroundDrawable) {
            setBackground(normalBackgroundDrawable);
            if (progressBar.getVisibility() != GONE) {
                progressBar.setVisibility(GONE);
            }
        }
    }

    public void updateProgress(float progress){
        progressBar.setProgress(progress);
    }

    public Song getCurrentSongObject() {
        return currentSongObject;
    }

    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(DisplayUtility.dp(50),MeasureSpec.EXACTLY));
//    }
}

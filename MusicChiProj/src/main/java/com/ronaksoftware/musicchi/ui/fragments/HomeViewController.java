package com.ronaksoftware.musicchi.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ronaksoftware.musicchi.R;
import com.ronaksoftware.musicchi.controllers.EventController;
import com.ronaksoftware.musicchi.controllers.SearchHistoryController;
import com.ronaksoftware.musicchi.controllers.SoundRecognizer;
import com.ronaksoftware.musicchi.controllers.recognizer.RecognizeResult;
import com.ronaksoftware.musicchi.models.SearchHistory;
import com.ronaksoftware.musicchi.ui.components.CircleProgressBar;
import com.ronaksoftware.musicchi.ui.components.CombinedDrawable;
import com.ronaksoftware.musicchi.ui.presenter.ActionBar;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.ui.presenter.Theme;
import com.ronaksoftware.musicchi.utils.DisplayUtility;
import com.ronaksoftware.musicchi.utils.LayoutHelper;
import com.ronaksoftware.musicchi.utils.Timer;
import com.ronaksoftware.musicchi.utils.TypefaceUtility;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


public class HomeViewController extends BaseViewController implements Timer.Delegate {

    private FrameLayout contentView;
    private TextView startTextView;
    private FrameLayout recognizeButtonContainer;

    private CompositeDisposable disposables = new CompositeDisposable();

    private boolean recognizeInProgress = false;

    private final int MIN_PROGRESS = DisplayUtility.dp(100);

    private int[] ripplesProgress = new int[]{DisplayUtility.dp(160), DisplayUtility.dp(217), DisplayUtility.dp(266)};

    private GradientDrawable centerRecognizeBackground;
    private CircleProgressBar timeoutProgress;
    private Timer timeoutTimer;

    @Override
    public boolean onFragmentCreate() {
        timeoutTimer = new Timer(this);
        disposables.add(EventController.recognizeResult.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RecognizeResult>() {
            @Override
            public void accept(RecognizeResult result) throws Exception {
                if (result instanceof RecognizeResult.Success) {
                    RecognizeResult.Success success = (RecognizeResult.Success) result;

                    if (success.getSoundSearchResponse() != null && success.getSoundSearchResponse().getInfo() != null) {
                        SearchHistory searchHistory = new SearchHistory();
                        searchHistory.setArtist(success.getSoundSearchResponse().getInfo().getArtists().get(0));
                        searchHistory.setReleaseDate(success.getSoundSearchResponse().getInfo().getReleaseDate());
                        searchHistory.setTitle(success.getSoundSearchResponse().getInfo().getTitle());

                        SearchHistoryController.getInstance().data.add(searchHistory);
                        SearchHistoryController.getInstance().save();

                        presentFragment(new RecognizeResultViewController(success.getSoundSearchResponse()));

                        startTextView.setText("برای شروع لمس کنید");
                    }

                    SoundRecognizer.getInstance().stopRecognize();
                } else if (result instanceof RecognizeResult.Error) {
                    RecognizeResult.Error error = (RecognizeResult.Error) result;
                    if (error.getErrorCode() == 2005) {
                        SoundRecognizer.getInstance().stopRecognize();
                    }
                } else if (result instanceof RecognizeResult.ServerError) {

                }
            }
        }));

        disposables.add(EventController.recognizeStatusChanged.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                recognizeInProgress = aBoolean;
                if (!recognizeInProgress) {
                    ripplesProgress = new int[]{DisplayUtility.dp(160), DisplayUtility.dp(217), DisplayUtility.dp(266)};
                }
                recognizeButtonContainer.invalidate();

                if (recognizeInProgress) {
                    timeoutProgress.setVisibility(View.VISIBLE);
                    timeoutProgress.setProgress(15000);
                    timeoutTimer.startProgressTimer();
                    startTextView.setText("در حال پیدا کردن موزیک در حال پخش...");
                } else {
                    timeoutTimer.stopProgressTimer();
                    timeoutProgress.setVisibility(View.GONE);
                    startTextView.setText("برای شروع لمس کنید");
                }
            }
        }));

        return super.onFragmentCreate();
    }

    @Override
    public void onFragmentDestroy() {
        disposables.dispose();
        timeoutTimer.destroy();
        timeoutTimer = null;
        super.onFragmentDestroy();
    }

    @Override
    protected ActionBar createActionBar(Context context) {
        return null;
    }

    @Override
    public View createView(Context context) {
        fragmentView = contentView = new FrameLayout(context);
        contentView.setBackground(new ColorDrawable(Color.parseColor("#1A1E2A")));


        FrameLayout actionBarView = new FrameLayout(context);
        contentView.addView(actionBarView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 56, Gravity.TOP | Gravity.LEFT, 0, Build.VERSION.SDK_INT >= 21 ? 24 : 0, 0, 0));
        actionBarView.setBackground(Theme.createRoundRectDrawable(new float[]{0, 0, DisplayUtility.dp(18), 0}, Color.parseColor("#FD0C6B")));

        TextView titleTextView = new TextView(context);
        titleTextView.setText("موزیکچی");
        titleTextView.setTextColor(Color.WHITE);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        titleTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        actionBarView.addView(titleTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL | Gravity.RIGHT, 0, 0, 20, 0));

        ImageView restoreButton = new ImageView(context);
        restoreButton.setScaleType(ImageView.ScaleType.CENTER);
        restoreButton.setImageResource(R.drawable.restore);
        restoreButton.setBackground(Theme.createSelectorDrawable(Color.WHITE));
        actionBarView.addView(restoreButton, LayoutHelper.createFrame(56, 56, Gravity.CENTER_VERTICAL | Gravity.LEFT, 60 - 12, 0, 0, 0));
        restoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentFragment(new SearchHistoryViewController());
            }
        });

        ImageView threeDotButton = new ImageView(context);
        threeDotButton.setScaleType(ImageView.ScaleType.CENTER);
        threeDotButton.setImageResource(R.drawable.threedot);
        threeDotButton.setBackground(Theme.createSelectorDrawable(Color.WHITE));
        actionBarView.addView(threeDotButton, LayoutHelper.createFrame(56, 56, Gravity.CENTER_VERTICAL | Gravity.LEFT, 0, 0, 0, 0));
        threeDotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentFragment(new SettingsViewController());
            }
        });


        recognizeButtonContainer = new FrameLayout(context) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                if (recognizeInProgress) {
//                    int alpha = 255;
//                    int scale = 0;

                    int maxProgress = DisplayUtility.dp(266);

                    for (int i = 0; i < ripplesProgress.length; i++) {
                        if (ripplesProgress[i] >= maxProgress) {
                            ripplesProgress[i] = MIN_PROGRESS;
                        }

                        int rippleProgress = ripplesProgress[i];
                        float percent = ((float) rippleProgress / (float) maxProgress);

                        int size = (int) (percent * maxProgress);
                        int alpha = 255 - ((int) ((percent) * 255));


                        centerRecognizeBackground.setAlpha(alpha);

                        int left = (getMeasuredWidth() / 2) - (size / 2);
                        int top = (getMeasuredHeight() / 2) - (size / 2);
                        int right = left + size;
                        int bottom = top + size;

                        centerRecognizeBackground.setCornerRadius(size / 2);
                        centerRecognizeBackground.setBounds(left, top, right, bottom);
                        centerRecognizeBackground.draw(canvas);

                        ripplesProgress[i] += DisplayUtility.dp(2);
                    }

                    postInvalidateDelayed(20);
                } else {
                    for (int i = 0; i < 3; i++) {
                        int width = 0;

                        if (i == 0) {
                            centerRecognizeBackground.setAlpha(100);
                            width = DisplayUtility.dp(160);
                        } else if (i == 1) {
                            centerRecognizeBackground.setAlpha(70);
                            width = DisplayUtility.dp(217);
                        } else {
                            centerRecognizeBackground.setAlpha(50);
                            width = DisplayUtility.dp(266);
                        }

                        int left = (getMeasuredWidth() / 2) - (width / 2);
                        int top = (getMeasuredHeight() / 2) - (width / 2);
                        int right = left + width;
                        int bottom = top + width;

                        centerRecognizeBackground.setCornerRadius(width / 2);
                        centerRecognizeBackground.setBounds(left, top, right, bottom);
                        centerRecognizeBackground.draw(canvas);
                    }
                }
            }
        };
        recognizeButtonContainer.setWillNotDraw(false);

        ImageView recognizeImageView = new ImageView(context);
        recognizeImageView.setImageResource(R.drawable.logo_white);
        recognizeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!recognizeInProgress) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        boolean hasAudio = getParentActivity().checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;

                        if (!hasAudio) {
                            getParentActivity().requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 3);
                            return;
                        }
                    }


                    SoundRecognizer.getInstance().startRecognize();
                    recognizeInProgress = true;
                } else {
                    SoundRecognizer.getInstance().stopRecognize();
                }
            }
        });
        centerRecognizeBackground = new GradientDrawable();
        centerRecognizeBackground.setCornerRadius(DisplayUtility.dp(50));
        centerRecognizeBackground.setColors(new int[]{0xFFFF73AA, 0xFFFD0C6B});
        centerRecognizeBackground.setAlpha(255);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(DisplayUtility.dp(50));
        gradientDrawable.setColors(new int[]{0xFFFF73AA, 0xFFFD0C6B});
        recognizeImageView.setBackground(gradientDrawable);

        recognizeImageView.setScaleType(ImageView.ScaleType.CENTER);
        recognizeButtonContainer.addView(recognizeImageView, LayoutHelper.createFrame(100, 100, Gravity.CENTER));

        timeoutProgress = new CircleProgressBar(context);
        timeoutProgress.setColor(Color.WHITE);
        timeoutProgress.setMin(0);
        timeoutProgress.setMax(1);
        timeoutProgress.setStrokeWidth(DisplayUtility.dp(4));
        timeoutProgress.setProgress(0);
        timeoutProgress.setVisibility(View.GONE);

        recognizeButtonContainer.addView(timeoutProgress, LayoutHelper.createFrame(100, 100, Gravity.CENTER));
        contentView.addView(recognizeButtonContainer, LayoutHelper.createFrame(266, 266, Gravity.CENTER));

        startTextView = new TextView(context);
        startTextView.setText("برای شروع لمس کنید");
        startTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans.ttf"));
        startTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        startTextView.setTextColor(Color.parseColor("#E5E9F5"));
        contentView.addView(startTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0, 0, 12));


        return fragmentView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (recognizeInProgress) {
            SoundRecognizer.getInstance().stopRecognize();
        }
    }

    @Override
    public void onProgress(float value) {
        timeoutProgress.setProgress(value);
    }

    @Override
    public long getCurrentState() {
        long currentTime = SoundRecognizer.getInstance().getCurrentTime();
        if (currentTime == -1) {
            return 0;
        }
        return currentTime;
    }
}

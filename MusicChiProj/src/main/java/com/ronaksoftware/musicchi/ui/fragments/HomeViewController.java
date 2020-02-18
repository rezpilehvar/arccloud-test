package com.ronaksoftware.musicchi.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ronaksoftware.musicchi.controllers.EventController;
import com.ronaksoftware.musicchi.controllers.SoundRecognizer;
import com.ronaksoftware.musicchi.controllers.recognizer.RecognizeResult;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.utils.LayoutHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


public class HomeViewController extends BaseViewController {

    private FrameLayout contentView;

    private TextView resultTextView;

    private CompositeDisposable disposables = new CompositeDisposable();

    private Button startRecordButton;
    private Button stopRecordButton;

    @Override
    public boolean onFragmentCreate() {
        disposables.add(EventController.recognizeResult.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RecognizeResult>() {
            @Override
            public void accept(RecognizeResult result) throws Exception {
                if (result instanceof RecognizeResult.Success) {
                    RecognizeResult.Success success = (RecognizeResult.Success) result;

                    if (success.getSoundSearchResponse() != null && success.getSoundSearchResponse().getInfo() != null) {
                        String artist = "";
                        if (success.getSoundSearchResponse().getInfo().getArtists() != null && !success.getSoundSearchResponse().getInfo().getArtists().isEmpty()) {
                            artist = success.getSoundSearchResponse().getInfo().getArtists().get(0);
                        }

                        resultTextView.setText(artist + " : " + success.getSoundSearchResponse().getInfo().getTitle());
                    }

                    SoundRecognizer.getInstance().stopRecognize();
                }else if (result instanceof RecognizeResult.Error) {

                }else if (result instanceof RecognizeResult.ServerError) {

                }
            }
        }));

        disposables.add(EventController.recognizeStatusChanged.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    actionBar.setTitle("Recognizing...");
                    startRecordButton.setEnabled(false);
                    stopRecordButton.setEnabled(true);
                }else {
                    actionBar.setTitle("MusicChi!");
                    stopRecordButton.setEnabled(false);
                    startRecordButton.setEnabled(true);
                }
            }
        }));

        return super.onFragmentCreate();
    }

    @Override
    public void onFragmentDestroy() {
        disposables.dispose();
        super.onFragmentDestroy();
    }

    @Override
    public View createView(Context context) {
        actionBar.setTitle("MusicChi!");
        fragmentView = contentView = new FrameLayout(context);

        startRecordButton = new Button(context);
        startRecordButton.setText("Start record");
        startRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    boolean hasAudio = getParentActivity().checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;

                    if (!hasAudio) {
                        getParentActivity().requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 3);
                        return;
                    }
                }

                start();
            }
        });

        contentView.addView(startRecordButton, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL | Gravity.LEFT, 16, 0, 0, 0));

        stopRecordButton = new Button(context);
        stopRecordButton.setText("Stop record");
        stopRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        stopRecordButton.setEnabled(false);

        contentView.addView(stopRecordButton, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL | Gravity.RIGHT, 0, 0, 16, 0));


        resultTextView = new TextView(context);
        resultTextView.setTextColor(Color.BLACK);
        contentView.addView(resultTextView,LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT,LayoutHelper.WRAP_CONTENT,Gravity.TOP | Gravity.CENTER_HORIZONTAL , 16 ,16,16,0));



        return fragmentView;
    }

    public void start() {
        resultTextView.setText("");
        SoundRecognizer.getInstance().startRecognize();
    }

    public void cancel() {
        SoundRecognizer.getInstance().stopRecognize();
    }
}

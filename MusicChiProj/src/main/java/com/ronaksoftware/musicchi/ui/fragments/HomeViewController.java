package com.ronaksoftware.musicchi.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.controllers.VoiceController;
import com.ronaksoftware.musicchi.network.MusicChiApi;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.utils.LayoutHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeViewController extends BaseViewController {
    private Disposable recordDisposable;
    private FrameLayout contentView;


    @Override
    public void onFragmentDestroy() {
        if (recordDisposable != null) {
            recordDisposable.dispose();
        }
        super.onFragmentDestroy();
    }

    @Override
    public View createView(Context context) {
        fragmentView = contentView = new FrameLayout(context);


        Button startRecordButton = new Button(context);
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

                recordDisposable = VoiceController.getInstance().startRecording().subscribeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.equals("RECORD_START")) {

                        }else {
                            sendRequest(s);
                        }
                    }
                });
            }
        });

        contentView.addView(startRecordButton, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL | Gravity.LEFT, 16, 0, 0, 0));

        Button stopRecordButton = new Button(context);
        stopRecordButton.setText("Stop record");
        stopRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recordDisposable != null) {
                    recordDisposable.dispose();
                }

                VoiceController.getInstance().stopRecording();
            }
        });

        contentView.addView(stopRecordButton, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL | Gravity.RIGHT, 0, 0, 16, 0));

        return fragmentView;
    }

    private CompositeDisposable requestDisposables = new CompositeDisposable();

    private void sendRequest(String encoded) {

        requestDisposables.add(ApplicationLoader.musicChiApi.listRepos(encoded).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i("Response", "success : " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }));
    }
}

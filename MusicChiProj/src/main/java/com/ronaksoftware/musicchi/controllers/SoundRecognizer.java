package com.ronaksoftware.musicchi.controllers;

import android.util.Log;

import com.ronaksoftware.musicchi.controllers.recognizer.RecognizeResult;
import com.ronaksoftware.musicchi.controllers.recognizer.RecognizerThread;
import com.ronaksoftware.musicchi.controllers.recognizer.RecorderConfig;
import com.ronaksoftware.musicchi.controllers.recognizer.data.AudioRecorderDataSource;

public class SoundRecognizer implements RecognizerThread.Delegate {
    private static volatile SoundRecognizer Instance;

    public static SoundRecognizer getInstance() {
        SoundRecognizer localInstance = Instance;
        if (localInstance == null) {
            synchronized (SoundRecognizer.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new SoundRecognizer();
                }
            }
        }
        return localInstance;
    }

    private volatile RecognizerThread recognizerThread = null;
    private AudioRecorderDataSource dataSource;
    private RecorderConfig recorderConfig;
    private volatile boolean recognizeInProgress = false;
    private volatile boolean recordInProgress = false;


    private SoundRecognizer() {
        recorderConfig = new RecorderConfig();
        dataSource = new AudioRecorderDataSource(recorderConfig);
    }

    public boolean startRecognize() {
        if (this.recognizeInProgress)
            return true;

        try {
            if (!recordInProgress) {
                try {
                    dataSource.init();
                    recordInProgress = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    recordInProgress = false;
                }
            }

            if (!recordInProgress) {
                return false;
            }

            this.recognizerThread = new RecognizerThread(dataSource, recorderConfig, this);
            this.recognizerThread.start();
            recognizeInProgress = true;
            recordInProgress = true;
        } catch (Exception e) {
            recordInProgress = false;
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void stopRecognize() {
        try {
            Log.d("SoundRecognizer", "cancel recognize");
            this.recognizeInProgress = false;
            if (this.recognizerThread != null) {
                this.recognizerThread.reqCancel();
                this.recognizerThread = null;
            }
            if (recordInProgress) {
                this.dataSource.release();
                recordInProgress = false;
            }
            if (this.dataSource != null) {
                this.dataSource.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getCurrentTime() {
        if (recognizerThread != null && recognizeInProgress) {
            return recognizerThread.getRecognizeTime();
        }
        return -1;
    }

    @Override
    public void onResult(RecognizeResult result) {
        EventController.recognizeResult.onNext(result);
    }

    @Override
    public void onStatusChanged(boolean recognizing) {
        EventController.recognizeStatusChanged.onNext(recognizing);
    }
}

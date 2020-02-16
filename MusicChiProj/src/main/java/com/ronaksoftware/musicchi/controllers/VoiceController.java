package com.ronaksoftware.musicchi.controllers;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;

import com.acrcloud.utils.ACRCloudExtrTool;
import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.errors.RecordError;
import com.ronaksoftware.musicchi.utils.DispatchQueue;
import com.ronaksoftware.musicchi.utils.Queues;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Action;

public class VoiceController {
    private static volatile VoiceController Instance;

    public static VoiceController getInstance() {
        VoiceController localInstance = Instance;
        if (localInstance == null) {
            synchronized (VoiceController.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new VoiceController();
                }
            }
        }
        return localInstance;
    }

    private AudioManager audioManager;

    private DispatchQueue recordQueue = new DispatchQueue("recordQueue");
    private Runnable recordStartRunnable;
    private Runnable recordRunnable;

    private AudioRecord audioRecorder;
    private long recordStartTime;
    private long lastFingurePrintTime;

    private ByteBuffer sendBuffer;
    private int recordBufferSize = 1280;

    private static final int AUDIO_NO_FOCUS_NO_DUCK = 0;

    private boolean hasRecordAudioFocus;
    private int hasAudioFocus;
    private int audioFocus = AUDIO_NO_FOCUS_NO_DUCK;
    private boolean resumeAudioOnFocusGain;
    private AudioManager.OnAudioFocusChangeListener audioRecordFocusChangedListener = focusChange -> {
        if (focusChange != AudioManager.AUDIOFOCUS_GAIN) {
            hasRecordAudioFocus = false;
        }
    };
    private View feedbackView;

    private VoiceController() {
        try {
            audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(Context.AUDIO_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Observable<String> startRecording() {

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                requestAudioFocus(true);

                try {
                    feedbackView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                } catch (Exception ignore) {

                }

                recordQueue.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (audioRecorder != null) {
                            Queues.runOnUIThread(() -> {
                                recordStartRunnable = null;
                                emitter.onError(new RecordError("record start error"));
                            });
                            return;
                        }

                        try {
                            audioRecorder = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, 16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, recordBufferSize * 10);
                            recordStartTime = System.currentTimeMillis();
                            lastFingurePrintTime = 0;

                            audioRecorder.startRecording();
                        } catch (Exception e) {
                            e.printStackTrace();
                            stopRecording();

                            try {
                                audioRecorder.release();
                                audioRecorder = null;
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }

                            emitter.onError(new RecordError("recordStartError"));
                            return;
                        }

                        recordQueue.postRunnable(recordRunnable = new Runnable() {
                            @Override
                            public void run() {
                                if (audioRecorder != null) {
                                    ByteBuffer buffer = sendBuffer;
                                    sendBuffer = null;
                                    if (buffer == null) {
                                        buffer = ByteBuffer.allocateDirect(recordBufferSize);
                                        buffer.order(ByteOrder.nativeOrder());
                                    }
                                    buffer.rewind();
                                    int len = audioRecorder.read(buffer, buffer.capacity());
                                    if (len > 0) {
                                        buffer.limit(len);

                                        if (outputStream == null) {
                                            outputStream = new ByteArrayOutputStream();
                                        }

                                        try {
                                            outputStream.write(buffer.array());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        boolean oneSecondPassed = recordStartTime < System.currentTimeMillis() -  1000;
                                        long oneSecondAgo = System.currentTimeMillis() - (1000);

                                        if (oneSecondPassed && lastFingurePrintTime < oneSecondAgo) {
                                            lastFingurePrintTime = System.currentTimeMillis();
                                            byte[] fingerprintBytes = ACRCloudExtrTool.createFingerprint(outputStream.toByteArray(),outputStream.size(),false);
                                            emitter.onNext(new String(fingerprintBytes));
                                        }

//                                        Log.i("Record", "outputStream Size : " + outputStream.size());
//
//                                        Log.i("Record", "progress");

                                        recordQueue.postRunnable(recordRunnable);
                                    } else {
                                        // end of recording
                                    }
                                }
                            }
                        });
                        Queues.runOnUIThread(() -> {
                            recordStartRunnable = null;
                            emitter.onNext("RECORD_START");
                        });
                    }
                });
            }
        }).doOnDispose(new Action() {
            @Override
            public void run() throws Exception {
                stopRecording();
            }
        });
    }

    private ByteArrayOutputStream outputStream;

    public void stopRecording() {

    }

    public void setFeedbackView(View view, boolean set) {
        if (set) {
            feedbackView = view;
        } else if (feedbackView == view) {
            feedbackView = null;
        }
    }


    public void requestAudioFocus(boolean request) {
        if (request) {
            if (!hasRecordAudioFocus && shouldRequestRecordAudioFocus()) {
                int result = audioManager.requestAudioFocus(audioRecordFocusChangedListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    hasRecordAudioFocus = true;
                }
            }
        } else {
            if (hasRecordAudioFocus) {
                audioManager.abandonAudioFocus(audioRecordFocusChangedListener);
                hasRecordAudioFocus = false;
            }
        }
    }

    private boolean shouldRequestRecordAudioFocus() {
        try {
            if (/*NotificationsController.audioManager.isWiredHeadsetOn() || */audioManager.isBluetoothA2dpOn()) {
                return false;
            }
        } catch (Throwable ignore) {

        }
        return true;
    }
}

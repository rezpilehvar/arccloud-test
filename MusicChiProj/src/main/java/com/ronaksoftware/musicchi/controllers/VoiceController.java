package com.ronaksoftware.musicchi.controllers;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.view.HapticFeedbackConstants;
import android.view.View;

import com.acrcloud.utils.ACRCloudExtrTool;
import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.errors.RecordError;
import com.ronaksoftware.musicchi.utils.DispatchQueue;
import com.ronaksoftware.musicchi.utils.Queues;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

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
    private long recordTimeCount;

    private ArrayList<ByteBuffer> recordBuffers = new ArrayList<>();
    private ByteBuffer fileBuffer;
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

    public Observable<String> startRecording(){

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
                            recordTimeCount = 0;
                            fileBuffer.rewind();

                            audioRecorder.startRecording();
                        }catch (Exception e){
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

                        recordQueue.postRunnable(recordRunnable =  new Runnable() {
                            @Override
                            public void run() {
                                if (audioRecorder != null) {
                                    ByteBuffer buffer;
                                    if (!recordBuffers.isEmpty()) {
                                        buffer = recordBuffers.get(0);
                                        recordBuffers.remove(0);
                                    } else {
                                        buffer = ByteBuffer.allocateDirect(recordBufferSize);
                                        buffer.order(ByteOrder.nativeOrder());
                                    }
                                    buffer.rewind();
                                    int len = audioRecorder.read(buffer, buffer.capacity());
                                    if (len > 0) {
                                        buffer.limit(len);
                                        double sum = 0;
                                        try {
                                            long newSamplesCount = samplesCount + len / 2;
                                            int currentPart = (int) (((double) samplesCount / (double) newSamplesCount) * recordSamples.length);
                                            int newPart = recordSamples.length - currentPart;
                                            float sampleStep;
                                            if (currentPart != 0) {
                                                sampleStep = (float) recordSamples.length / (float) currentPart;
                                                float currentNum = 0;
                                                for (int a = 0; a < currentPart; a++) {
                                                    recordSamples[a] = recordSamples[(int) currentNum];
                                                    currentNum += sampleStep;
                                                }
                                            }
                                            int currentNum = currentPart;
                                            float nextNum = 0;
                                            sampleStep = (float) len / 2 / (float) newPart;
                                            for (int i = 0; i < len / 2; i++) {
                                                short peak = buffer.getShort();
                                                if (peak > 2500) {
                                                    sum += peak * peak;
                                                }
                                                if (i == (int) nextNum && currentNum < recordSamples.length) {
                                                    recordSamples[currentNum] = peak;
                                                    nextNum += sampleStep;
                                                    currentNum++;
                                                }
                                            }
                                            samplesCount = newSamplesCount;
                                        } catch (Exception e) {
                                            FileLog.e(e);
                                        }
                                        buffer.position(0);
                                        final double amplitude = Math.sqrt(sum / len / 2);
                                        final ByteBuffer finalBuffer = buffer;
                                        final boolean flush = len != buffer.capacity();
                                        if (len != 0) {
                                            fileEncodingQueue.postRunnable(() -> {
                                                while (finalBuffer.hasRemaining()) {
                                                    int oldLimit = -1;
                                                    if (finalBuffer.remaining() > fileBuffer.remaining()) {
                                                        oldLimit = finalBuffer.limit();
                                                        finalBuffer.limit(fileBuffer.remaining() + finalBuffer.position());
                                                    }
                                                    fileBuffer.put(finalBuffer);
                                                    if (fileBuffer.position() == fileBuffer.limit() || flush) {
                                                        if (writeFrame(fileBuffer, !flush ? fileBuffer.limit() : finalBuffer.position()) != 0) {
                                                            fileBuffer.rewind();
                                                            recordTimeCount += fileBuffer.limit() / 2 / 16;
                                                        }
                                                    }
                                                    if (oldLimit != -1) {
                                                        finalBuffer.limit(oldLimit);
                                                    }
                                                }
                                                recordQueue.postRunnable(() -> recordBuffers.add(finalBuffer));
                                            });
                                        }
                                        recordQueue.postRunnable(recordRunnable);
                                        AndroidUtilities.runOnUIThread(() -> NotificationCenter.getInstance(recordingCurrentAccount).postNotificationName(NotificationCenter.recordProgressChanged, recordingGuid, System.currentTimeMillis() - recordStartTime, amplitude));
                                    } else {
                                        recordBuffers.add(buffer);
                                        if (sendAfterDone != 3) {
                                            stopRecordingInternal(sendAfterDone, sendAfterDoneNotify, sendAfterDoneScheduleDate);
                                        }
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
        });
    }

    public void stopRecording(){

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

package com.ronaksoftware.musicchi.controllers.recognizer;

import android.util.Base64;
import android.util.Log;

import com.acrcloud.rec.engine.ACRCloudUniversalEngine;
import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.controllers.recognizer.data.AudioRecorderDataSourceInterface;
import com.ronaksoftware.musicchi.network.ErrorResponse;
import com.ronaksoftware.musicchi.network.ResponseEnvelope;
import com.ronaksoftware.musicchi.network.response.SoundSearchResponse;
import com.ronaksoftware.musicchi.utils.Queues;
import com.ronaksoftware.musicchi.utils.TypeUtility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class RecognizerThread extends Thread {
    public interface Delegate {
        void onResult(RecognizeResult result);
        void onStatusChanged(boolean recognizing);
    }

    public static final int fpTime = 3000;

    private RecorderConfig mConfig;
    private Delegate delegate;

    private AudioRecorderDataSourceInterface mAudioDataSource;
    private ByteArrayOutputStream mAudioBufferStream = new ByteArrayOutputStream();

    private volatile boolean mCancel = false;
    private volatile boolean mStop = false;
    private int mNextRecginzeLen = 0;
    private long mStartRecognizeTime = 0L;


    public RecognizerThread(AudioRecorderDataSourceInterface audioDataSource, RecorderConfig config, Delegate workerListener) {
        this.mAudioDataSource = audioDataSource;
        this.mConfig = config;
        this.delegate = workerListener;
        setDaemon(true);
    }

    public void reqCancel() {
        this.mCancel = true;
    }

    public void reqStop() {
        this.mStop = true;
    }

    private void reset() {
        try {
            this.mCancel = false;
            this.mStop = false;
            if (this.mAudioBufferStream != null) {
                this.mAudioBufferStream.close();
                this.mAudioBufferStream = null;
            }
            if (this.mAudioDataSource != null)
                this.mAudioDataSource.setStatus(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean startRecognize() {
        Log.d("AudioRecorder", "startRecognize");
        int rate = this.mConfig.rate;
        int channels = this.mConfig.channels;
        this.mNextRecginzeLen = fpTime * rate * channels * 2 / 1000;
        return true;
    }

    private void resumeRecognize() {
        if (this.mConfig == null)
            return;
        int rate = this.mConfig.rate;
        int channels = this.mConfig.channels;
        int maxRecognizeBuffer = this.mConfig.recordOnceMaxTimeMS / 1000 * rate * channels * 2;
        boolean isTry = false;
        while (!this.mCancel) {
            byte[] data = null;
            try {
                data = this.mAudioDataSource.getAudioData();
            } catch (Exception ex) {
                Queues.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        if (delegate != null) {
                            delegate.onResult(new RecognizeResult.Error(2000, "get audio data error"));
                        }
                    }
                });
                break;
            }
            try {
                if (data != null)
                    this.mAudioBufferStream.write(data);

                if (this.mAudioDataSource.hasAudioData())
                    continue;
            } catch (Exception ex) {
                Queues.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        if (delegate != null) {
                            delegate.onResult(new RecognizeResult.Error(2000, ex));
                        }
                    }
                });
                break;
            }
            if (this.mCancel)
                break;
            int curBufferLen = this.mAudioBufferStream.size();
            long curTotalTime = System.currentTimeMillis() - this.mStartRecognizeTime;
            if (curTotalTime < this.mConfig.sessionTotalTimeoutMS) {
                if ((curBufferLen >= this.mNextRecginzeLen && !this.mCancel) || this.mStop) {
                    byte[] curBuffer = this.mAudioBufferStream.toByteArray();
                    int curBufferFPLen = curBuffer.length;
                    if (curBufferFPLen > maxRecognizeBuffer)
                        curBufferFPLen = maxRecognizeBuffer;
                    if (this.mStop)
                        isTry = true;

                    Log.i("AudioBufferSize", "curBuffer size : " + curBuffer.length + " currBufferFPLen : " + curBufferFPLen);
                    RecognizeResult response = sendRecognize(curBuffer, curBufferFPLen);
                    if (this.mStop) {
                        if (!isTry && !(response instanceof RecognizeResult.Success) && this.mAudioDataSource.hasAudioData())
                            continue;

                        Queues.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                if (delegate != null) {
                                    delegate.onResult(new RecognizeResult.Error(0, "Unknown error"));
                                }
                            }
                        });
                        break;
                    }
                    if (response instanceof RecognizeResult.Success) {
                        this.mCancel = true;

                        Queues.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                if (delegate != null) {
                                    delegate.onResult(response);
                                }
                            }
                        });
                        break;
                    }
                    this.mNextRecginzeLen = (int) ((fpTime / 1000 * rate * channels) * 2.0D);
                }
                continue;
            }
            if (curTotalTime > this.mConfig.sessionTotalTimeoutMS || curBufferLen >= maxRecognizeBuffer) {
//                this.mStartRecognizeTime = System.currentTimeMillis();
                Queues.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        if (delegate != null) {
                            RecognizeResult recognizeResult = new RecognizeResult.Error(2005, "restart , byte buffer is full!");

                            delegate.onResult(recognizeResult);
                        }
                    }
                });
                if (!this.mCancel)
                    startRecognize();
                this.mAudioBufferStream.reset();
            }
        }
    }

    private String getFinger(byte[] buffer, int bufferLen) {
        byte[] fpBuffer = ACRCloudUniversalEngine.createFingerprint(buffer, bufferLen, this.mConfig.muteThreshold, false);

        Log.d("AudioRecorder", "create fingerprint end");
        if (fpBuffer == null) {
            if (bufferLen * 1000 / this.mConfig.rate * this.mConfig.channels * 2 > this.mConfig.recMuteMaxTimeMS)
                return null;
            fpBuffer = new byte[8];
        }

        return Base64.encodeToString(fpBuffer, 0);
    }

    public RecognizeResult sendRecognize(byte[] buffer, int bufferLen) {
        String fingerBase64 = getFinger(buffer, bufferLen);
        if (fingerBase64 == null) {
            return new RecognizeResult.Error(2004);
        }

        Call<ResponseEnvelope<SoundSearchResponse>> callSync = ApplicationLoader.musicChiApi.searchByFingerprint(fingerBase64);
        try {
            Response<ResponseEnvelope<SoundSearchResponse>> response = callSync.execute();
            if (response.isSuccessful() && response.body() != null) {
                return new RecognizeResult.Success(response.body().getPayload());
            } else {
                if (response.errorBody() != null) {
                    ErrorResponse errorResponse = TypeUtility.parseErrorResponse(response.errorBody().charStream());

                    return new RecognizeResult.ServerError(errorResponse);
                }

                return new RecognizeResult.Error("Server Error but no error response");
            }

        } catch (IOException e) {
            return new RecognizeResult.Error(1000, e);
        }
    }

    public void run() {
        super.run();
        Queues.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (delegate != null) {
                    delegate.onStatusChanged(true);
                }
            }
        });
        this.mStartRecognizeTime = System.currentTimeMillis();
        try {
            this.mAudioDataSource.init();
            this.mAudioDataSource.setStatus(true);
        } catch (Exception ex) {
            Queues.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    if (delegate != null) {
                        delegate.onResult(new RecognizeResult.Error(ex.toString()));
                    }
                }
            });
            return;
        }
        if (startRecognize())
            resumeRecognize();
        reset();
        Queues.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (delegate != null) {
                    delegate.onStatusChanged(false);
                }
            }
        });
    }

    public long getRecognizeTime() {
        return System.currentTimeMillis() - this.mStartRecognizeTime;
    }

}

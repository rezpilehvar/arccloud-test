package com.ronaksoftware.musicchi.controllers.recognizer.data;

import android.util.Log;

import com.ronaksoftware.musicchi.controllers.recognizer.recorder.RecorderDefault;
import com.ronaksoftware.musicchi.controllers.recognizer.recorder.RecorderInterface;
import com.ronaksoftware.musicchi.controllers.recognizer.RecorderConfig;
import com.ronaksoftware.musicchi.controllers.recognizer.RecorderError;
import com.ronaksoftware.musicchi.controllers.recognizer.utils.RecognizerUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class AudioRecorderDataSource implements AudioRecorderDataSourceInterface{

    public interface Delegate {
        void onVolumeChanged(double paramDouble);

        void onRecordDataAvailable(byte[] paramArrayOfbyte);
    }

    private volatile BlockingQueue<byte[]> mAudioQueue = new LinkedBlockingQueue<>();

    private volatile ACRCloudRecordThread mACRCloudRecordThread = null;

    private RecorderInterface mACRCloudRecorderEngine = null;

    private RecorderConfig mConfig = null;

    private volatile boolean isRecording = false;

    private volatile boolean isActive = false;

    private Delegate mACRCloudAudioDataSourceListener = null;

    public AudioRecorderDataSource(RecorderConfig config) {
        this.mConfig = config;
    }

    public AudioRecorderDataSource(RecorderConfig config, Delegate audioListener) {
        this.mConfig = config;
        this.mACRCloudAudioDataSourceListener = audioListener;
    }

    @Override
    public void init() throws RecorderError {
        boolean ret = false;
        try {
            ret = startRecording();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!ret) {
            release();
            throw new RecorderError(2000);
        }
    }

    public byte[] getAudioData() throws Exception {
        byte[] buffer = null;
        for (int i = 0; i < this.mConfig.retryRecorderReadMaxNum; i++) {
            try {
                buffer = this.mAudioQueue.poll(200L, TimeUnit.MILLISECONDS);
                if (buffer != null)
                    return buffer;
                Log.e("AudioRecorder", "getAudioData null retry read num = " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        release();
        throw new RecorderError(2000);
    }

    @Override
    public boolean hasAudioData() {
        return (this.mAudioQueue.size() > 0);
    }

    @Override
    public boolean putAudioData(byte[] buffer) {
        return true;
    }

    @Override
    public void release() {
        stopRecording();
    }

    @Override
    public void setStatus(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public void clear() {
        try {
            if (this.mAudioQueue != null)
                this.mAudioQueue.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean initRecorder(RecorderConfig config) {
        if (this.mACRCloudRecorderEngine != null)
            this.mACRCloudRecorderEngine.release();

        this.mACRCloudRecorderEngine = (RecorderInterface) new RecorderDefault();
        if (!this.mACRCloudRecorderEngine.init(config)) {
            Log.e("AudioRecorder", "record init error");
            this.mACRCloudRecorderEngine = null;
            return false;
        }
        return true;
    }

    private boolean startRecording() {
        if (this.isRecording)
            return true;
        if (this.mACRCloudRecorderEngine == null && !initRecorder(this.mConfig))
            return false;
        if (this.mACRCloudRecorderEngine == null)
            return false;
        if (!this.mACRCloudRecorderEngine.startRecording()) {
            this.mACRCloudRecorderEngine.release();
            this.mACRCloudRecorderEngine = null;
            return false;
        }
        try {
            if (this.mACRCloudRecordThread == null) {
                this.mACRCloudRecordThread = new ACRCloudRecordThread(this.mConfig);
                this.mACRCloudRecordThread.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.mACRCloudRecorderEngine.release();
            this.mACRCloudRecorderEngine = null;
            return false;
        }
        this.isRecording = true;
        return true;
    }

    private void stopRecording() {
        try {
            this.isRecording = false;
            if (this.mACRCloudRecordThread != null) {
                this.mACRCloudRecordThread.stopRecord();
                this.mACRCloudRecordThread.join(2000L);
                this.mACRCloudRecordThread = null;
                this.mAudioQueue.clear();
            }
            if (this.mACRCloudRecorderEngine != null) {
                this.mACRCloudRecorderEngine.release();
                this.mACRCloudRecorderEngine = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ACRCloudRecordThread extends Thread {
        private volatile boolean isRecording = false;

        private RecorderConfig mConfig = null;

        public ACRCloudRecordThread(RecorderConfig config) {
            this.mConfig = config;
            setDaemon(true);
        }

        public void stopRecord() {
            this.isRecording = false;
        }

        public void onAudioDataAvailable(byte[] buffer) {
            try {
                int oneBufferTime = AudioRecorderDataSource.this.mACRCloudRecorderEngine.getAudioBufferSize() * 1000 / this.mConfig.rate * this.mConfig.channels * 2;
                if (!AudioRecorderDataSource.this.isActive && AudioRecorderDataSource.this.mAudioQueue.size() >= this.mConfig.reservedRecordBufferMS / oneBufferTime + 2)
                    AudioRecorderDataSource.this.mAudioQueue.poll();
                if (AudioRecorderDataSource.this.isActive && AudioRecorderDataSource.this.mACRCloudAudioDataSourceListener != null && this.mConfig.isVolumeCallback) {
                    double volume = RecognizerUtils.calculateVolume(buffer, buffer.length);
                    AudioRecorderDataSource.this.mACRCloudAudioDataSourceListener.onVolumeChanged(volume);
                }
//                if (AudioRecorderDataSource.this.isActive && AudioRecorderDataSource.this.mACRCloudAudioDataSourceListener != null && this.mConfig.acrcloudRecordDataListener != null && buffer != null)
//                    AudioRecorderDataSource.this.mACRCloudAudioDataSourceListener.onRecordDataAvailable(buffer);
                AudioRecorderDataSource.this.mAudioQueue.put(buffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                this.isRecording = true;
                int retryReadNum = 5;
                while (this.isRecording) {
                    if (AudioRecorderDataSource.this.mACRCloudRecorderEngine == null) {
                        this.isRecording = false;
                        break;
                    }
                    byte[] buffer = AudioRecorderDataSource.this.mACRCloudRecorderEngine.read();
                    if (buffer == null) {
                        if (retryReadNum > 0) {
                            retryReadNum--;
                            continue;
                        }
                        this.isRecording = false;
                        break;
                    }
                    retryReadNum = 5;
                    onAudioDataAvailable(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.isRecording = false;
            }
        }
    }
}

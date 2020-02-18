package com.ronaksoftware.musicchi.controllers.recognizer.recorder;

import android.media.AudioRecord;
import android.os.Build;
import android.util.Log;

import com.ronaksoftware.musicchi.controllers.recognizer.RecorderConfig;

public class RecorderDefault implements RecorderInterface {
  private static final String TAG = "RecorderDefault";
  
  private AudioRecord mAudioRecord = null;
  
  private RecorderConfig mConfig = null;
  
  private int mMinBufferSize = 0;
  
  public boolean init(RecorderConfig config) {
    try {
      int audioFormat = 16;
      if (config.channels == 2)
        audioFormat = 12; 
      this.mMinBufferSize = AudioRecord.getMinBufferSize(config.rate, audioFormat, 2);
      Log.e("RecorderDefault", "system min buffer size: " + this.mMinBufferSize);
      if (config.volumeCallbackIntervalMS > 0) {
        int tBufferSize = config.volumeCallbackIntervalMS * config.rate * config.channels * 2 / 1000;
        if (tBufferSize > this.mMinBufferSize)
          this.mMinBufferSize = tBufferSize; 
      }
      Log.e("RecorderDefault", "min buffer size: " + this.mMinBufferSize);
      Log.e("RecorderDefault", "rate: " + config.rate + "; channels=" + config.channels);
      this.mAudioRecord = new AudioRecord(config.source, config.rate, audioFormat, 2, this.mMinBufferSize);
      if (Build.VERSION.SDK_INT >= 23 && 
        config.audioDeviceInfo != null)
        this.mAudioRecord.setPreferredDevice(config.audioDeviceInfo);
      if (this.mAudioRecord.getState() != 1) {
        release();
        return false;
      } 
      this.mConfig = config;
    } catch (Exception e) {
      e.printStackTrace();
      this.mAudioRecord = null;
      return false;
    } 
    return true;
  }
  
  public void release() {
    try {
      if (this.mAudioRecord != null) {
        this.mAudioRecord.release();
        this.mAudioRecord = null;
        Log.e("RecorderDefault", "releaseAudioRecord");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void stopRecording() {
    try {
      if (this.mAudioRecord != null && this.mAudioRecord
        .getRecordingState() == 3)
        this.mAudioRecord.stop(); 
      release();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public boolean startRecording() {
    try {
      Log.e("RecorderDefault", "startRecording");
      for (int i = 0; i < this.mConfig.initMaxRetryNum; i++) {
        Log.e("RecorderDefault", "Try get AudioRecord : " + i);
        if (this.mAudioRecord != null || init(this.mConfig)) {
          if (this.mAudioRecord.getRecordingState() != 3)
            this.mAudioRecord.startRecording(); 
          if (this.mAudioRecord.getRecordingState() == 3)
            break; 
          Log.e("RecorderDefault", "Start record error!");
          release();
        } 
      } 
      if (this.mAudioRecord == null)
        return false; 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return true;
  }
  
  public int getAudioBufferSize() {
    return this.mMinBufferSize;
  }
  
  public byte[] read() {
    byte[] buffer = null;
    int len = 0;
    try {
      if (this.mAudioRecord != null) {
        buffer = new byte[this.mMinBufferSize];
        len = this.mAudioRecord.read(buffer, 0, this.mMinBufferSize);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    if (len <= 0) {
      Log.e("RecorderDefault", "read buffer error AudioRecord ret = " + len);
      buffer = null;
    } 
    if (buffer != null && len != buffer.length) {
      Log.d("RecorderDefault", "len != buffer.length " + len + " " + buffer.length);
      byte[] tBuffer = new byte[len];
      System.arraycopy(buffer, 0, tBuffer, 0, len);
      buffer = tBuffer;
    } 
    return buffer;
  }
}

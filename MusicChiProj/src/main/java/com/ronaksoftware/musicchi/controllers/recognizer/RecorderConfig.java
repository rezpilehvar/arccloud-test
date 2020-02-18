package com.ronaksoftware.musicchi.controllers.recognizer;

import android.media.AudioDeviceInfo;

public class RecorderConfig {
    public int channels = 1;

    public int rate = 8000;

    public int source = 1;

    public AudioDeviceInfo audioDeviceInfo = null;

    public int volumeCallbackIntervalMS = 100;

    public boolean isVolumeCallback = true;

    public int initMaxRetryNum = 5;

    public int recMuteMaxTimeMS = 10000;

    public int retryRecorderReadMaxNum = 15;

    public int reservedRecordBufferMS = 3000;

    public int recordOnceMaxTimeMS = 12000;

    public int sessionTotalTimeoutMS = 30000;

    public int muteThreshold = 50;

    public int card = 0;

    public int device = 0;

    public int periodSize = 1024;

    public int periods = 4;
}

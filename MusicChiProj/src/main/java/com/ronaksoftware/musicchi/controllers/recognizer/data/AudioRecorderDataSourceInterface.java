package com.ronaksoftware.musicchi.controllers.recognizer.data;

public interface AudioRecorderDataSourceInterface {
    void init() throws Exception;

    boolean putAudioData(byte[] paramArrayOfbyte);

    byte[] getAudioData() throws Exception;

    boolean hasAudioData();

    void release();

    void setStatus(boolean paramBoolean);

    void clear();
}

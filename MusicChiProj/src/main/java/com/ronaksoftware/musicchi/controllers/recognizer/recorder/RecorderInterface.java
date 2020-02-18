package com.ronaksoftware.musicchi.controllers.recognizer.recorder;

import com.ronaksoftware.musicchi.controllers.recognizer.RecorderConfig;

public interface RecorderInterface {
  boolean init(RecorderConfig paramACRCloudConfig);
  
  void release();
  
  void stopRecording();
  
  boolean startRecording();
  
  int getAudioBufferSize();
  
  byte[] read();
}

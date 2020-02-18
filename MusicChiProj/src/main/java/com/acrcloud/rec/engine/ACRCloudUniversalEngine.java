package com.acrcloud.rec.engine;

import android.util.Log;

public class ACRCloudUniversalEngine {
  private static final String TAG = "ACRCloudUniversalEngine";
  
  static {
    try {
      System.loadLibrary("ACRCloudUniversalEngine");
    } catch (Exception e) {
      System.err.println("ACRCloudUniversalEngine loadLibrary error!");
    } 
  }
  
  public static byte[] createFingerprint(byte[] buffer, int bufferLen, String ekey, String skey, int muteThreshold, boolean isFixOptimizing) {
    if (buffer == null || bufferLen <= 0)
      return null; 
    if (bufferLen > buffer.length)
      bufferLen = buffer.length; 
    return native_create_fingerprint(buffer, bufferLen, muteThreshold, ekey, skey, isFixOptimizing);
  }
  
  public static byte[] createFingerprint(byte[] buffer, int bufferLen, int muteThreshold, boolean isFixOptimizing) {
    if (buffer == null || bufferLen <= 0)
      return null; 
    if (bufferLen > buffer.length)
      bufferLen = buffer.length; 
    return native_create_fingerprint(buffer, bufferLen, muteThreshold, null, null, isFixOptimizing);
  }
  
  public static byte[] createFingerprint(byte[] buffer, int bufferLen, int rate, int channels, String ekey, String skey, int muteThreshold, int quality, boolean isFixOptimizing) {
    Log.e("SoundRecognizer", "bufferLen=" + bufferLen + "; rate=" + rate + "; channels=" + channels + "; quality=" + quality);
    if (buffer == null || bufferLen <= 0)
      return null; 
    if (bufferLen > buffer.length)
      bufferLen = buffer.length; 
    if (rate != 8000 || channels != 1) {
      byte[] tBuffer = resample(buffer, bufferLen, rate, channels, quality);
      if (tBuffer == null)
        return null; 
      buffer = tBuffer;
      bufferLen = buffer.length;
    } 
    return native_create_fingerprint(buffer, bufferLen, muteThreshold, ekey, skey, isFixOptimizing);
  }
  
  public static byte[] createFingerprint(byte[] buffer, int bufferLen, int rate, int channels, int muteThreshold, int quality, boolean isFixOptimizing) {
    if (buffer == null || bufferLen <= 0)
      return null; 
    if (bufferLen > buffer.length)
      bufferLen = buffer.length; 
    if (rate != 8000 || channels != 1) {
      byte[] tBuffer = resample(buffer, bufferLen, rate, channels, quality);
      if (tBuffer == null)
        return null; 
      buffer = tBuffer;
      bufferLen = buffer.length;
    } 
    return native_create_fingerprint(buffer, bufferLen, muteThreshold, null, null, isFixOptimizing);
  }
  
  public static byte[] createHummingFingerprint(byte[] buffer, int bufferLen, int rate, int channels, int quality, boolean isFixOptimizing) {
    if (buffer == null || bufferLen <= 0)
      return null; 
    if (bufferLen > buffer.length)
      bufferLen = buffer.length; 
    if (rate != 8000 || channels != 1) {
      byte[] tBuffer = resample(buffer, bufferLen, rate, channels, quality);
      if (tBuffer == null)
        return null; 
      buffer = tBuffer;
      bufferLen = buffer.length;
    } 
    return native_create_humming_fingerprint(buffer, bufferLen, isFixOptimizing);
  }
  
  public static byte[] resample(byte[] buffer, int bufferLen, int sampleRate, int nChannels, int quality) {
    if (buffer == null || bufferLen <= 0)
      return null; 
    if (bufferLen > buffer.length)
      bufferLen = buffer.length; 
    if (sampleRate == 8000 && nChannels == 1)
      return buffer; 
    return native_resample(buffer, bufferLen, nChannels, sampleRate, quality);
  }
  
  public static String encrypt(String value, String key) {
    if (value == null || "".equals(value))
      return null; 
    byte[] valueBytes = value.getBytes();
    byte[] keyBytes = key.getBytes();
    byte[] re = native_encrypt(valueBytes, valueBytes.length, keyBytes, keyBytes.length);
    if (re == null)
      return null; 
    return new String(re);
  }
  
  public static void setLog(boolean isLog) {
    native_set_log(isLog);
  }
  
  protected native long native_tinyalsa_init(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
  
  protected native int native_tinyalsa_get_buffer_size(long paramLong);
  
  protected native int native_tinyalsa_get_recording_state(long paramLong);
  
  protected native byte[] native_tinyalsa_read(long paramLong, int paramInt);
  
  protected native void native_tinyalsa_release(long paramLong);
  
  protected static native byte[] native_create_fingerprint(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean);
  
  protected static native byte[] native_create_humming_fingerprint(byte[] paramArrayOfbyte, int paramInt, boolean paramBoolean);
  
  protected static native byte[] native_resample(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  protected static native byte[] native_encrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2);
  
  protected static native void native_set_log(boolean paramBoolean);
}

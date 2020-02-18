package com.ronaksoftware.musicchi.controllers.recognizer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RecognizerUtils {
  public static double calculateVolume(byte[] buffer, int len) {
    int step = 3;
    int size = len >> step;
    float fDB = 0.0F, mu = 0.0F;
    for (int i = 0; i < size; i++) {
      short retVal = (short)(buffer[(i << step) + 1] << 8);
      retVal = (short)(retVal | buffer[i << step] & 0xFF);
      int temp = (retVal ^ retVal >> 15) - (retVal >> 15);
      fDB += (temp * temp);
      mu += temp;
    } 
    fDB /= size;
    mu /= size;
    double db = Math.log10((fDB - mu * mu + 1.0F));
    db = Math.min(db, 8.0D);
    return db / 8.0D;
  }
  
  public static boolean isNetworkConnected(Context context) {
    if (context != null) {
      ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      if (mNetworkInfo != null)
        return mNetworkInfo.isAvailable(); 
    } 
    return false;
  }
  
  public static byte[] pcm2Wav(byte[] pcmBuffer, int sampleRate, int nchannels) {
    if (pcmBuffer == null)
      return null; 
    int pcmBufferLen = pcmBuffer.length;
    int totalAudiolen = pcmBufferLen + 36;
    int byteRate = 16 * sampleRate * nchannels / 8;
    byte[] header = new byte[44];
    header[0] = 82;
    header[1] = 73;
    header[2] = 70;
    header[3] = 70;
    header[4] = (byte)(totalAudiolen & 0xFF);
    header[5] = (byte)(totalAudiolen >> 8 & 0xFF);
    header[6] = (byte)(totalAudiolen >> 16 & 0xFF);
    header[7] = (byte)(totalAudiolen >> 24 & 0xFF);
    header[8] = 87;
    header[9] = 65;
    header[10] = 86;
    header[11] = 69;
    header[12] = 102;
    header[13] = 109;
    header[14] = 116;
    header[15] = 32;
    header[16] = 16;
    header[17] = 0;
    header[18] = 0;
    header[19] = 0;
    header[20] = 1;
    header[21] = 0;
    header[22] = (byte)nchannels;
    header[23] = 0;
    header[24] = (byte)(sampleRate & 0xFF);
    header[25] = (byte)(sampleRate >> 8 & 0xFF);
    header[26] = (byte)(sampleRate >> 16 & 0xFF);
    header[27] = (byte)(sampleRate >> 24 & 0xFF);
    header[28] = (byte)(byteRate & 0xFF);
    header[29] = (byte)(byteRate >> 8 & 0xFF);
    header[30] = (byte)(byteRate >> 16 & 0xFF);
    header[31] = (byte)(byteRate >> 24 & 0xFF);
    header[32] = (byte)(nchannels * 16 / 8);
    header[33] = 0;
    header[34] = 16;
    header[35] = 0;
    header[36] = 100;
    header[37] = 97;
    header[38] = 116;
    header[39] = 97;
    header[40] = (byte)(pcmBufferLen & 0xFF);
    header[41] = (byte)(pcmBufferLen >> 8 & 0xFF);
    header[42] = (byte)(pcmBufferLen >> 16 & 0xFF);
    header[43] = (byte)(pcmBufferLen >> 24 & 0xFF);
    byte[] wavBuffer = new byte[pcmBufferLen + header.length];
    System.arraycopy(header, 0, wavBuffer, 0, header.length);
    System.arraycopy(pcmBuffer, 0, wavBuffer, header.length, pcmBuffer.length);
    return wavBuffer;
  }
  
  public static void createFileWithByte(byte[] bytes, String fileName) {
    File file = new File(fileName);
    FileOutputStream outputStream = null;
    BufferedOutputStream bufferedOutputStream = null;
    try {
      if (file.exists())
        file.delete(); 
      file.createNewFile();
      outputStream = new FileOutputStream(file);
      bufferedOutputStream = new BufferedOutputStream(outputStream);
      bufferedOutputStream.write(bytes);
      bufferedOutputStream.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (outputStream != null)
        try {
          outputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }  
      if (bufferedOutputStream != null)
        try {
          bufferedOutputStream.close();
        } catch (Exception e2) {
          e2.printStackTrace();
        }  
    } 
  }
}

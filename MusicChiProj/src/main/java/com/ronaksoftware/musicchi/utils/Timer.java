package com.ronaksoftware.musicchi.utils;

import com.google.android.exoplayer2.C;
import com.ronaksoftware.musicchi.controllers.EventController;
import com.ronaksoftware.musicchi.models.Song;

import java.util.TimerTask;

public class Timer {
    private java.util.Timer progressTimer = null;
    private final Object progressTimerSync = new Object();
    private final Object sync = new Object();
    private Delegate delegate;

    private int duration = 30000;

    public interface Delegate {
        void onProgress(float value);

        long getCurrentState();
    }

    public Timer(Delegate delegate) {
        this.delegate = delegate;
    }

    public void destroy() {
        delegate = null;
    }

    public void stopProgressTimer() {
        synchronized (progressTimerSync) {
            if (progressTimer != null) {
                try {
                    progressTimer.cancel();
                    progressTimer = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startProgressTimer() {
        synchronized (progressTimerSync) {
            if (progressTimer != null) {
                try {
                    progressTimer.cancel();
                    progressTimer = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            progressTimer = new java.util.Timer();
            progressTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    synchronized (sync) {
                        Queues.runOnUIThread(() -> {
                            try {
                                long progress = delegate.getCurrentState();
                                float value = duration >= 0 ? (progress / (float) duration) : 0.0f;
                                if (progress < 0) {
                                    return;
                                }

                                delegate.onProgress(value);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }, 0, 17);
        }
    }
}

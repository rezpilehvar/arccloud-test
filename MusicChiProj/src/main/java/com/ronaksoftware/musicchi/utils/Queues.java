package com.ronaksoftware.musicchi.utils;

import com.ronaksoftware.musicchi.ApplicationLoader;

public class Queues {
    public static volatile DispatchQueue global = new DispatchQueue("globalQueue");

    public static void runOnUIThread(Runnable runnable) {
        ApplicationLoader.applicationHandler.post(runnable);
    }

    public static void runOnUIThread(Runnable runnable, long delayedTime) {
        ApplicationLoader.applicationHandler.postDelayed(runnable, delayedTime);
    }

    public static void cancelRunOnUIThread(Runnable runnable) {
        ApplicationLoader.applicationHandler.removeCallbacks(runnable);
    }
}

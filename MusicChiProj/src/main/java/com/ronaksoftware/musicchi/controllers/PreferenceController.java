package com.ronaksoftware.musicchi.controllers;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ronaksoftware.musicchi.ApplicationLoader;

public class PreferenceController {
    private static volatile PreferenceController Instance;

    public static PreferenceController getInstance() {
        PreferenceController localInstance = Instance;
        if (localInstance == null) {
            synchronized (PreferenceController.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new PreferenceController();
                }
            }
        }
        return localInstance;
    }

    private SharedPreferences mainPreferences;

    private PreferenceController() {
        mainPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
    }

    public SharedPreferences getMainPreferences() {
        return mainPreferences;
    }
}

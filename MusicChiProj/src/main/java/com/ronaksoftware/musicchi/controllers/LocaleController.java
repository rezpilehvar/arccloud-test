package com.ronaksoftware.musicchi.controllers;

import androidx.annotation.StringRes;

import com.ronaksoftware.musicchi.ApplicationLoader;

public class LocaleController {
    public static boolean isRTL = false;

    public static String getString(String key, @StringRes int id) {
        return getString(id);
    }

    public static String getString(@StringRes int id) {
        return ApplicationLoader.applicationContext.getResources().getString(id);
    }
}

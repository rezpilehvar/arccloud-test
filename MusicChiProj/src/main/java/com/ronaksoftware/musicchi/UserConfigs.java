package com.ronaksoftware.musicchi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class UserConfigs {
    public static String userID;
    public static String phoneNumber;
    public static String username;
    public static String sessionID;

    public static boolean showBilipLink;
    public static boolean vasEnabled;
    public static String storeLink;

    static {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
        userID = preferences.getString("AUTH_USER_ID", null);
        phoneNumber = preferences.getString("AUTH_USER_NUMBER", null);
        username = preferences.getString("AUTH_USER_USERNAME", null);
        sessionID = preferences.getString("AUTH_USER_SESSION_ID", null);

        showBilipLink = preferences.getBoolean("CONFIGS_SHOW_BILIP_LINK", false);
        vasEnabled = preferences.getBoolean("CONFIGS_VAS_ENABLED", true);
        storeLink = preferences.getString("CONFIGS_STORE_LINK", null);
    }

    public static boolean isAuthenticated() {
        if (sessionID != null) {
            return true;
        }
        return false;
    }

    @SuppressLint("ApplySharedPref")
    public static void save() {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("AUTH_USER_ID", userID);
        editor.putString("AUTH_USER_NUMBER", phoneNumber);
        editor.putString("AUTH_USER_USERNAME", username);
        editor.putString("AUTH_USER_SESSION_ID", sessionID);


        editor.putBoolean("CONFIGS_SHOW_BILIP_LINK", showBilipLink);
        editor.putBoolean("CONFIGS_VAS_ENABLED", vasEnabled);
        editor.putString("CONFIGS_STORE_LINK", storeLink);

        editor.commit();
    }

    public static void clearUser() {
        userID = null;
        phoneNumber = null;
        username = null;
        sessionID = null;
    }
}

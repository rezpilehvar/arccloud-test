package com.ronaksoftware.musicchi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class UserConfigs {
    public static String userID;
    public static String phoneNumber;
    public static String username;
    public static String sessionID;

    static {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
        userID = preferences.getString("AUTH_USER_ID", null);
        phoneNumber = preferences.getString("AUTH_USER_NUMBER", null);
        username = preferences.getString("AUTH_USER_USERNAME", null);
        sessionID = preferences.getString("AUTH_USER_SESSION_ID", null);
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

        editor.commit();
    }

    public static void clearUser() {
        userID = null;
        phoneNumber = null;
        username = null;
        sessionID = null;
    }
}

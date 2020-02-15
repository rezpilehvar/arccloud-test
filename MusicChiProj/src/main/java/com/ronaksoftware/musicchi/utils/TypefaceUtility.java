package com.ronaksoftware.musicchi.utils;

import android.graphics.Typeface;
import android.os.Build;

import com.ronaksoftware.musicchi.ApplicationLoader;

import java.util.Hashtable;

public class TypefaceUtility {
    private static final Hashtable<String, Typeface> typefaceCache = new Hashtable<>();

    public static Typeface getTypeface(String assetPath) {
        synchronized (typefaceCache) {
            if (!typefaceCache.containsKey(assetPath)) {
                try {
                    Typeface t;
                    if (Build.VERSION.SDK_INT >= 26) {
                        Typeface.Builder builder = new Typeface.Builder(ApplicationLoader.applicationContext.getAssets(), assetPath);
                        if (assetPath.contains("medium")) {
                            builder.setWeight(700);
                        }
                        if (assetPath.contains("italic")) {
                            builder.setItalic(true);
                        }
                        t = builder.build();
                    } else {
                        t = Typeface.createFromAsset(ApplicationLoader.applicationContext.getAssets(), assetPath);
                    }
                    typefaceCache.put(assetPath, t);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return typefaceCache.get(assetPath);
        }
    }
}

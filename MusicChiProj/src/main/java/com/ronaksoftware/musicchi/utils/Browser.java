package com.ronaksoftware.musicchi.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Browser {

    public static void openUrl(Context context, String url) {
        if (url == null) {
            return;
        }
        openUrl(context, Uri.parse(url));
    }

    public static void openUrl(final Context context, final Uri uri) {
        if (context == null || uri == null) {
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.putExtra(android.provider.Browser.EXTRA_CREATE_NEW_TAB, true);
            intent.putExtra(android.provider.Browser.EXTRA_APPLICATION_ID, context.getPackageName());
            intent.setData(uri);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

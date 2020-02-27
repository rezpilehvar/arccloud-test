package com.ronaksoftware.musicchi.utils;

import com.google.gson.reflect.TypeToken;
import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.network.ErrorResponse;

import java.io.Reader;
import java.lang.reflect.Type;


public class TypeUtility {
    public static ErrorResponse parseErrorResponse(Reader data) {
        if (data == null) {
            return null;
        }

        Type type = new TypeToken<ErrorResponse>() {
        }.getType();
        return ApplicationLoader.gson.fromJson(data, type);
    }
}

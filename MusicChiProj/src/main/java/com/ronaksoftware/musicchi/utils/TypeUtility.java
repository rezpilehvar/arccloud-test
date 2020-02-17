package com.ronaksoftware.musicchi.utils;

import com.google.gson.reflect.TypeToken;
import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.network.ErrorResponse;

import java.lang.reflect.Type;

import retrofit2.Response;

public class TypeUtility {
    public static ErrorResponse parseErrorResponse(Response response) {
        Type type = new TypeToken<ErrorResponse>() {
        }.getType();
        assert response.errorBody() != null;
        return ApplicationLoader.gson.fromJson(response.errorBody().charStream(), type);
    }
}

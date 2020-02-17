package com.ronaksoftware.musicchi.network.request;

import com.google.gson.annotations.SerializedName;

public class LogoutRequest {
    @SerializedName("unsubscribe")
    private boolean unsubscribe;

    public static LogoutRequest create(boolean unsubscribe) {
        LogoutRequest request = new LogoutRequest();
        request.unsubscribe = unsubscribe;
        return request;
    }
}

package com.ronaksoftware.musicchi.network;

import com.google.gson.annotations.SerializedName;

public class ResponseEnvelope<R> {
    public static final String errorConstructor = "err";

    @SerializedName("constructor")
    private String constructor;

    @SerializedName("payload")
    private R payload;

    public String error;

    public String getConstructor() {
        return constructor;
    }

    public R getPayload() {
        return payload;
    }
}

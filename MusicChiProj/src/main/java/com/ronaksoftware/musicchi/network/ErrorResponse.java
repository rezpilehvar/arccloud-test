package com.ronaksoftware.musicchi.network;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    @SerializedName("constructor")
    private String constroctor;

    @SerializedName("payload")
    private String payload;

    public String getConstroctor() {
        return constroctor;
    }

    public String getPayload() {
        return payload;
    }
}

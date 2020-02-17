package com.ronaksoftware.musicchi.network.response;

import com.google.gson.annotations.SerializedName;

public class BooleanResponse {
    @SerializedName("success")
    private boolean success;

    public boolean isSuccess() {
        return success;
    }
}

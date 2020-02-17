package com.ronaksoftware.musicchi.network.request;

import com.google.gson.annotations.SerializedName;

public class SendCodeRequest {
    @SerializedName("phone")
    public String phone;

    public static SendCodeRequest create(String phone) {
        SendCodeRequest loginRequest = new SendCodeRequest();
        loginRequest.phone = phone;
        return loginRequest;
    }
}

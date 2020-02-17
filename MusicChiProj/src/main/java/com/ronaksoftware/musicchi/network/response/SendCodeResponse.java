package com.ronaksoftware.musicchi.network.response;

import com.google.gson.annotations.SerializedName;

public class SendCodeResponse {
    @SerializedName("phone_code_hash")
    private String phoneCodeHash;

    @SerializedName("registered")
    private boolean registered;

    public String getPhoneCodeHash() {
        return phoneCodeHash;
    }

    public boolean isRegistered() {
        return registered;
    }
}

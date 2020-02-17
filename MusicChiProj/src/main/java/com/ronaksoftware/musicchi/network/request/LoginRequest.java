package com.ronaksoftware.musicchi.network.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("phone_code")
    private String phoneCode;
    @SerializedName("phone_code_hash")
    private String codeHash;
    @SerializedName("phone")
    private String phoneNumber;

    public static LoginRequest create(String phoneCode, String codeHash, String phoneNumber) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.phoneCode = phoneCode;
        loginRequest.codeHash = codeHash;
        loginRequest.phoneNumber = phoneNumber;
        return loginRequest;
    }
}

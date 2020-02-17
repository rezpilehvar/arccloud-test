package com.ronaksoftware.musicchi.network.request;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("phone_code")
    private String phoneCode;

    @SerializedName("phone_code_hash")
    private String phoneCodeHash;

    @SerializedName("phone")
    private String phoneNumber;

    @SerializedName("username")
    private String username;

    public static RegisterRequest create(String phoneCode, String phoneCodeHash, String phoneNumber, String username) {
        RegisterRequest request = new RegisterRequest();

        request.phoneCode = phoneCode;
        request.phoneCodeHash = phoneCodeHash;
        request.phoneNumber = phoneNumber;
        request.username = username;
        return request;
    }
}

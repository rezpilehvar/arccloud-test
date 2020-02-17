package com.ronaksoftware.musicchi.network.response;

import com.google.gson.annotations.SerializedName;

public class AuthorizationResponse {
    @SerializedName("user_id")
    private String userID;

    @SerializedName("phone")
    private String phone;

    @SerializedName("username")
    private String username;

    @SerializedName("session_id")
    private String serssionID;

    public String getUserID() {
        return userID;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getSerssionID() {
        return serssionID;
    }
}

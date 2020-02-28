package com.ronaksoftware.musicchi.network.response;

import com.google.gson.annotations.SerializedName;

public class ConfigResponse {
    @SerializedName("update_available")
    private boolean updateAvailable;

    @SerializedName("update_force")
    private boolean updateForce;

    @SerializedName("store_link")
    private String storeLink;

    @SerializedName("show_blip_link")
    private boolean showBlipLink;

    @SerializedName("authorized")
    private boolean authorized;

    @SerializedName("vas_enabled")
    private boolean vasEnabled;


    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public boolean isUpdateForce() {
        return updateForce;
    }

    public String getStoreLink() {
        return storeLink;
    }

    public boolean isShowBlipLink() {
        return showBlipLink;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public boolean isVasEnabled() {
        return vasEnabled;
    }
}

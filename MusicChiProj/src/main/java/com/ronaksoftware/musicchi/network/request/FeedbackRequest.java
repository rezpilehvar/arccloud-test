package com.ronaksoftware.musicchi.network.request;

import com.google.gson.annotations.SerializedName;

public class FeedbackRequest {
    @SerializedName("text")
    private String text;

    @SerializedName("rate")
    private int rate;

    public FeedbackRequest(String text, int rate) {
        this.text = text;
        this.rate = rate;
    }
}

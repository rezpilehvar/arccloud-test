package com.ronaksoftware.musicchi.models;

import com.google.gson.annotations.SerializedName;

public class SearchHistory {
    @SerializedName("artist")
    private
    String artist;

    @SerializedName("title")
    private
    String title;

    @SerializedName("releaseDate")
    private
    String releaseDate;

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}

package com.ronaksoftware.musicchi.models;

import com.google.gson.annotations.SerializedName;

public class Song {
    @SerializedName("id")
    private
    String id;

    @SerializedName("title")
    private
    String title;

    @SerializedName("genre")
    private
    String genre;

    @SerializedName("lyrics")
    private
    String lyrics;

    @SerializedName("artists")
    private
    String artists;


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getLyrics() {
        return lyrics;
    }

    public String getArtists() {
        return artists;
    }
}

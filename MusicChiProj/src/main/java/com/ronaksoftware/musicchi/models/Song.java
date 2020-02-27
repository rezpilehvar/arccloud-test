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


    // Player
    public int audioPlayerDuration;
    public float audioProgress;
    public int audioProgressSec;
    public float bufferedProgress;

    public void resetPlayingProgress() {
        audioProgress = 0.0f;
        audioProgressSec = 0;
        bufferedProgress = 0.0f;
        audioPlayerDuration = 0;
    }

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

package com.ronaksoftware.musicchi.network.response;

import com.google.gson.annotations.SerializedName;
import com.ronaksoftware.musicchi.models.Song;

import java.util.ArrayList;

public class SoundSearchResponse {
    @SerializedName("info")
    private
    Info info;

    @SerializedName("songs")
    private
    ArrayList<Song> songs;

    public static class Info {
        @SerializedName("artists")
        private ArrayList<String> artists;

        @SerializedName("title")
        private String title;

        @SerializedName("release_date")
        private String releaseDate;

        public ArrayList<String> getArtists() {
            return artists;
        }

        public String getTitle() {
            return title;
        }

        public String getReleaseDate() {
            return releaseDate;
        }
    }

    public Info getInfo() {
        return info;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
}

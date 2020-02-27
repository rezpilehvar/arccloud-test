package com.ronaksoftware.musicchi.network.response;

import com.google.gson.annotations.SerializedName;
import com.ronaksoftware.musicchi.models.Song;

import java.util.ArrayList;

public class SearchSongsListResponse {
    @SerializedName("songs")
    ArrayList<Song> songs = new ArrayList<>();

    public ArrayList<Song> getSongs() {
        return songs;
    }
}

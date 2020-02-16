package com.ronaksoftware.musicchi.network;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MusicChiApi {

    @POST("/music/search/sound")
    Observable<String> listRepos(@Query("sound") String base64Encoded);
}

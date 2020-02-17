package com.ronaksoftware.musicchi.network;

import com.ronaksoftware.musicchi.network.request.LoginRequest;
import com.ronaksoftware.musicchi.network.request.SendCodeRequest;
import com.ronaksoftware.musicchi.network.response.AuthorizationResponse;
import com.ronaksoftware.musicchi.network.response.SendCodeResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MusicChiApi {

    @POST("/auth/send_code")
    Observable<ResponseEnvelope<SendCodeResponse>> sendCode(@Body SendCodeRequest sendCodeRequest);

    @POST("/auth/login")
    Observable<ResponseEnvelope<AuthorizationResponse>> login(@Body LoginRequest sendCodeRequest);

    @POST("/music/search/sound")
    Observable<String> listRepos(@Query("sound") String base64Encoded);
}

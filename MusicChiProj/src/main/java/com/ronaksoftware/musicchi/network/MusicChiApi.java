package com.ronaksoftware.musicchi.network;

import com.ronaksoftware.musicchi.network.request.LoginRequest;
import com.ronaksoftware.musicchi.network.request.LogoutRequest;
import com.ronaksoftware.musicchi.network.request.RegisterRequest;
import com.ronaksoftware.musicchi.network.request.SendCodeRequest;
import com.ronaksoftware.musicchi.network.response.AuthorizationResponse;
import com.ronaksoftware.musicchi.network.response.BooleanResponse;
import com.ronaksoftware.musicchi.network.response.SendCodeResponse;
import com.ronaksoftware.musicchi.network.response.SearchSongsListResponse;
import com.ronaksoftware.musicchi.network.response.SoundSearchResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MusicChiApi {

    @POST("/auth/send_code")
    Observable<ResponseEnvelope<SendCodeResponse>> sendCode(@Body SendCodeRequest sendCodeRequest);

    @POST("/auth/login")
    Observable<ResponseEnvelope<AuthorizationResponse>> login(@Body LoginRequest loginRequest);

    @POST("/auth/logout")
    Observable<ResponseEnvelope<BooleanResponse>> logout(@Body LogoutRequest logoutRequest);

    @POST("/auth/register")
    Observable<ResponseEnvelope<AuthorizationResponse>> register(@Body RegisterRequest registerRequest);

    @FormUrlEncoded
    @POST("/music/search/fingerprint")
    Call<ResponseEnvelope<SoundSearchResponse>> searchByFingerprint(@Field("fingerprint") String fingerprint);

    @GET("/music/search")
    Observable<ResponseEnvelope<SearchSongsListResponse>> search();
}

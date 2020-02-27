package com.ronaksoftware.musicchi;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ronaksoftware.musicchi.controllers.EventController;
import com.ronaksoftware.musicchi.controllers.MediaController;
import com.ronaksoftware.musicchi.controllers.PreferenceController;
import com.ronaksoftware.musicchi.controllers.SearchHistoryController;
import com.ronaksoftware.musicchi.controllers.SoundRecognizer;
import com.ronaksoftware.musicchi.network.ErrorResponse;
import com.ronaksoftware.musicchi.network.MusicChiApi;
import com.ronaksoftware.musicchi.utils.Queues;
import com.ronaksoftware.musicchi.utils.TypeUtility;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationLoader extends Application {
    public static volatile Context applicationContext;
    public static volatile Handler applicationHandler;

    public static volatile Retrofit retrofit;
    public static volatile Gson gson;
    public static volatile MusicChiApi musicChiApi;

    @Override
    public void onCreate() {
        try {
            applicationContext = getApplicationContext();
        } catch (Throwable ignore) {
        }
        super.onCreate();

        if (applicationContext == null) {
            applicationContext = getApplicationContext();
        }

        applicationHandler = new Handler(getMainLooper());


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request.Builder request = chain.request().newBuilder();

                request.addHeader("AccessKey", "8ynNr1zPWYEnRJEigKS3VKeUR7ptIpBQxkaP2mOhKBthGfpOTahq0skqeMHI4lUE");
                if (UserConfigs.isAuthenticated()) {
                    request.addHeader("SessionID", UserConfigs.sessionID);
                }

                Response response = chain.proceed(request.build());

                switch (response.code()) {
                    case 403:
                        if (response.body() != null) {
                            ErrorResponse errorResponse = TypeUtility.parseErrorResponse(response.body().charStream());

                            if (errorResponse != null) {
                                if (errorResponse.getPayload().equals("SESSION_INVALID")) {
                                    Queues.runOnUIThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            logout(true);
                                            Toast.makeText(applicationContext, "دوباره وارد شوید", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        }
                        throw new IOException("Unauthorized !!");
                }

                return response;
            }
        });

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);
        httpClient.connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        GsonBuilder gsonBuilder = new GsonBuilder();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson = gsonBuilder.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://V2.blipapi.xyz")
                .client(httpClient.build())
                .build();


        musicChiApi = retrofit.create(MusicChiApi.class);

        PreferenceController.getInstance();
        SearchHistoryController.getInstance();
    }

    public static void logout(boolean notifyViews) {
        UserConfigs.phoneNumber = null;
        UserConfigs.sessionID = null;
        UserConfigs.userID = null;
        UserConfigs.username = null;
        UserConfigs.save();
        SearchHistoryController.getInstance().data.clear();
        SearchHistoryController.getInstance().save();
        if (notifyViews) {
            EventController.authChanged.onNext(new Object());
        }
        SoundRecognizer.getInstance().stopRecognize();
        MediaController.getInstance().cleanupPlayer(false);
    }

    @SuppressLint("MissingPermission")
    public static boolean isNetworkOnline() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) ApplicationLoader.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && (netInfo.isConnectedOrConnecting() || netInfo.isAvailable())) {
                return true;
            }

            netInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                netInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }
}

package com.tas.speech;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.tas.speech.client.ApiTypes;
import com.tas.speech.client.RetrofitApi;
import com.tas.speech.client.SpeechClient;
import com.tas.speech.client.SpeechMockClient;
import com.tas.speech.client.SpeechRequestInterceptor;

import io.fabric.sdk.android.Fabric;
import retrofit.RestAdapter;

public class SpeechApp extends Application{

    private static RestAdapter restAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        init();
    }

    private void init() {
        if(restAdapter == null) {
            RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder()
                    .setEndpoint(Const.REST_ENDPOINT).
                            setRequestInterceptor(new SpeechRequestInterceptor(getApplicationContext()));
            if (Const.MOCK) {
                restAdapterBuilder.setClient(new SpeechMockClient(getApplicationContext(), Const.MOCK_RESPONSE_TIME));
            } else {
                restAdapterBuilder.setClient(new SpeechClient());
            }
            restAdapterBuilder.build();
            restAdapter = restAdapterBuilder.build();
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
    }

    public static RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public static RetrofitApi getApiOfType(ApiTypes type) {
        return type.getApiType();
    }
}

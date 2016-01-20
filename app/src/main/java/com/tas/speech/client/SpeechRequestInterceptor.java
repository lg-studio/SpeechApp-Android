package com.tas.speech.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.tas.speech.R;

import retrofit.RequestInterceptor;

public class SpeechRequestInterceptor implements RequestInterceptor {

    private Context mCtx;

    public SpeechRequestInterceptor(Context ctx) {
        mCtx = ctx;
    }

    @Override
    public void intercept(RequestFacade request) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mCtx);
        String authCookie = preferences.getString(mCtx.getString(R.string.auth_cookie), "");

        if(!authCookie.equals("")) {
           request.addHeader("Cookie", mCtx.getString(R.string.auth_cookie_name) + "=" + authCookie);
        }
    }
}

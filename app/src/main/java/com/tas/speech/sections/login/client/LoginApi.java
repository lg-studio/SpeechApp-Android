package com.tas.speech.sections.login.client;

import com.tas.speech.SpeechApp;
import com.tas.speech.client.RetrofitApi;
import com.tas.speech.sections.login.models.LoginResponse;
import com.tas.speech.sections.login.models.LogoutResponse;

import retrofit.Callback;
import retrofit.RestAdapter;

public class LoginApi extends RetrofitApi {

    private LoginService service;

    public LoginApi() {
        RestAdapter restAdapter = SpeechApp.getRestAdapter();
        service = restAdapter.create(LoginService.class);
    }

    public void login(String loginType, String email, String password, String oauthToken,
               Callback<LoginResponse> callBack) {
        service.login(loginType, email, password, oauthToken, callBack);
    }

    public void logout(Callback<LogoutResponse> callBack) {
        service.logout(callBack);
    }
}
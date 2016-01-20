package com.tas.speech.sections.login.client;

import com.tas.speech.sections.login.models.LoginResponse;
import com.tas.speech.sections.login.models.LogoutResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("/Login")
    void login(@Field("LoginType") String loginType, @Field("Email") String email,
               @Field("Password") String password, @Field("OauthToken") String oauthToken,
               Callback<LoginResponse> callBack);

    @POST("/Logout")
    void logout(Callback<LogoutResponse> callBack);

}

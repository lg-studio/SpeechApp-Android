package com.tas.speech.sections.account.client;

import com.tas.speech.sections.account.models.ResetPasswordResponse;
import com.tas.speech.sections.account.models.SingUpResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.mime.MultipartTypedOutput;

public interface AccountService {

    @POST("/PasswordReset")
    void passwordReset(@Body MultipartTypedOutput resetInfo, Callback<ResetPasswordResponse> passwordResetCallback);

    @POST("/SignUp")
    void signUp(
            @Body MultipartTypedOutput signUpData, Callback<SingUpResponse> signUpCallback);
}

package com.tas.speech.sections.account.client;

import com.tas.speech.SpeechApp;
import com.tas.speech.client.RetrofitApi;
import com.tas.speech.sections.account.models.ResetPasswordResponse;
import com.tas.speech.sections.account.models.SingUpResponse;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.mime.MultipartTypedOutput;

public class AccountApi extends RetrofitApi {

    private AccountService service;

    public AccountApi() {
        RestAdapter restAdapter = SpeechApp.getRestAdapter();
        service = restAdapter.create(AccountService.class);
    }

    public void passwordReset(MultipartTypedOutput resetInfo, Callback<ResetPasswordResponse> passwordResetCallback) {
        service.passwordReset(resetInfo, passwordResetCallback);
    }

    public void signUp(MultipartTypedOutput attachments, Callback<SingUpResponse> signUpCallback) {
        service.signUp(attachments, signUpCallback);
    }
}

package com.tas.speech.client;

import com.tas.speech.sections.account.client.AccountApi;
import com.tas.speech.sections.certificates.client.CertificatesApi;
import com.tas.speech.sections.game.client.GameApi;
import com.tas.speech.sections.login.client.LoginApi;
import com.tas.speech.sections.tasks.client.MultipleChoiceApi;
import com.tas.speech.sections.topic.client.TopicsApi;

public enum ApiTypes {

    GAME_API(new GameApi()), LOGIN_API(new LoginApi()), ACCOUNT_API(new AccountApi()),
    MULTIPLE_CHOICE_API(new MultipleChoiceApi()), TOPICS_API(new TopicsApi()),
    CERTIFICATES_API(new CertificatesApi());

    private final RetrofitApi instance;

    private ApiTypes(RetrofitApi instance) {
        this.instance = instance;
    }

    public RetrofitApi getApiType() {
        return instance;
    }

}
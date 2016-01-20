package com.tas.speech.sections.tasks.client;

import com.tas.speech.SpeechApp;
import com.tas.speech.client.RetrofitApi;
import com.tas.speech.sections.tasks.models.MultipleChoiceOptionsResponse;

import retrofit.Callback;
import retrofit.RestAdapter;


public class MultipleChoiceApi extends RetrofitApi {

    private MultipleChoiceService service;

    public MultipleChoiceApi() {
        RestAdapter restAdapter = SpeechApp.getRestAdapter();
        service = restAdapter.create(MultipleChoiceService.class);
    }

    public void multipleChoiceOptions(String taskId,
                               Callback<MultipleChoiceOptionsResponse> multipleChoiceOptionsCallback) {
        service.multipleChoiceOptions(taskId, multipleChoiceOptionsCallback);
    }
}
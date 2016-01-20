package com.tas.speech.sections.topic.client;


import com.tas.speech.SpeechApp;
import com.tas.speech.client.RetrofitApi;
import com.tas.speech.sections.topic.models.TasksNavTreeResponse;

import retrofit.Callback;
import retrofit.RestAdapter;

public class TopicsApi extends RetrofitApi {

    private TopicsService service;

    public TopicsApi() {
        RestAdapter restAdapter = SpeechApp.getRestAdapter();
        service = restAdapter.create(TopicsService.class);
    }

    public void taskNavigationTree(String certificateId, String certificateSubcategoryId,
                            Callback<TasksNavTreeResponse> nodeStructureCallback) {
        service.taskNavigationTree(certificateId, certificateSubcategoryId, nodeStructureCallback);
    }
}
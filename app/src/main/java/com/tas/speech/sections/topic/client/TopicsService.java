package com.tas.speech.sections.topic.client;

import com.tas.speech.sections.topic.models.TasksNavTreeResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface TopicsService {

    @GET("/TaskNavigationTree/{certificateId}/{certificateSubcategoryId}")
    void taskNavigationTree(@Path("certificateId") String certificateId,
                       @Path("certificateSubcategoryId") String certificateSubcategoryId,
                       Callback<TasksNavTreeResponse> nodeStructureCallback);
}

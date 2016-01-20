package com.tas.speech.sections.tasks.client;

import com.tas.speech.sections.tasks.models.MultipleChoiceOptionsResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by drago_000 on 05/08/2015.
 */
public interface MultipleChoiceService {

    @GET("/MultipleChoiceOptions/{taskId}")
    void multipleChoiceOptions(@Path("taskId") String taskId,
                       Callback<MultipleChoiceOptionsResponse> multipleChoiceOptionsCallback);
}

package com.tas.speech.sections.game.client;

import com.tas.speech.sections.game.models.GameStructureResponse;
import com.tas.speech.sections.game.models.TaskInputResponse;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;

/**
 * Created by drago_000 on 17/07/2015.
 */
public interface GameService {

    @GET("/TaskStructure/{taskTemplateId}")
    void taskStructure(@Path("taskTemplateId") String taskTemplateId,
                       Callback<GameStructureResponse> taskStructureCallback);

    @GET("/NodeStructure/{taskId}/{itemId}")
    void nodeStructure(@Path("taskId") String taskId, @Path("itemId") String itemId,
                       Callback<GameStructureResponse> nodeStructureCallback);

//    @Multipart
//    @POST("/TaskInput")
//    void taskInput(
//            @Part("Inputs") String inputs,
//            @PartMap Map<String, TypedFile> files, Callback<TaskInputResponse> taskInputCallback);

    @POST("/TaskInput")
    void taskInput(
            @Body MultipartTypedOutput attachments, Callback<TaskInputResponse> taskInputCallback);

}

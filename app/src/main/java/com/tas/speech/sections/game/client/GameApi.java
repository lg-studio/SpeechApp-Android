package com.tas.speech.sections.game.client;

import com.tas.speech.SpeechApp;
import com.tas.speech.client.RetrofitApi;
import com.tas.speech.sections.game.models.GameStructureResponse;
import com.tas.speech.sections.game.models.TaskInputResponse;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;

/**
 * Created by drago on 13/08/2015.
 */
public class GameApi extends RetrofitApi {

    private GameService service;

    public GameApi() {
        RestAdapter restAdapter = SpeechApp.getRestAdapter();
        service = restAdapter.create(GameService.class);
    }

    public void taskStructure(String taskTemplateId,
                       Callback<GameStructureResponse> taskStructureCallback) {
        service.taskStructure(taskTemplateId, taskStructureCallback);
    }

    public void nodeStructure(String taskId, String itemId,
                       Callback<GameStructureResponse> nodeStructureCallback) {
        service.nodeStructure(taskId, itemId, nodeStructureCallback);
    }

    public void taskInput(MultipartTypedOutput attachments,
                          Callback<TaskInputResponse> taskInputCallback) {
        service.taskInput(attachments, taskInputCallback);

    }
}

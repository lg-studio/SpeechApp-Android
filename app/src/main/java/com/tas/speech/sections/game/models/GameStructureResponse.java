package com.tas.speech.sections.game.models;

/**
 * Created by drago_000 on 17/07/2015.
 */
public class GameStructureResponse {
    private String StatusCode, Message;
    private GameStructureData Data;

    public GameStructureResponse() {

    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public GameStructureData getData() {
        return Data;
    }

    public void setData(GameStructureData data) {
        Data = data;
    }
}

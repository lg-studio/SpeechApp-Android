package com.tas.speech.sections.topic.models;

import java.io.Serializable;

/**
 * Created by drago_000 on 03/08/2015.
 */
public class TasksNavTreeResponse implements Serializable{
    private String StatusCode, Message;
    private TasksData Data;

    public TasksNavTreeResponse() {

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

    public TasksData getData() {
        return Data;
    }

    public void setData(TasksData data) {
        Data = data;
    }
}

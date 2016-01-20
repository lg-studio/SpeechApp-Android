package com.tas.speech.sections.tasks.models;

import java.io.Serializable;

/**
 * Created by drago_000 on 05/08/2015.
 */
public class MultipleChoiceOptionsResponse implements Serializable{
    String StatusCode, Message;
    MultipleChoiceOptionsData Data;

    public MultipleChoiceOptionsResponse() {

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

    public MultipleChoiceOptionsData getData() {
        return Data;
    }

    public void setData(MultipleChoiceOptionsData data) {
        Data = data;
    }
}

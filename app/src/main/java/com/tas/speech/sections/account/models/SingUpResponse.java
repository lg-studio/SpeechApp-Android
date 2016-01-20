package com.tas.speech.sections.account.models;

public class SingUpResponse {
    private String StatusCode, Message;

    public SingUpResponse() {

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
}

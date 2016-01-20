package com.tas.speech.sections.login.models;

/**
 * Created by drago on 12/08/2015.
 */
public class LogoutResponse {
    private String StatusCode, Message;

    public LogoutResponse() {

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

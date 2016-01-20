package com.tas.speech.sections.account.models;

/**
 * Created by drago on 21/08/2015.
 */
public class ResetPasswordResponse {
    private String StatusCode, Message;

    public ResetPasswordResponse() {

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

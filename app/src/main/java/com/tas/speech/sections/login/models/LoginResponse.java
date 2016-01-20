package com.tas.speech.sections.login.models;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private String StatusCode, Message;
    private LoginData Data;

    public LoginResponse() {

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

    public LoginData getData() {
        return Data;
    }

    public void setData(LoginData data) {
        Data = data;
    }
}

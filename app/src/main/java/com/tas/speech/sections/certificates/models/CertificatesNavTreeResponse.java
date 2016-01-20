package com.tas.speech.sections.certificates.models;

import java.io.Serializable;

/**
 * Created by drago_000 on 03/08/2015.
 */
public class CertificatesNavTreeResponse implements Serializable {

    private String StatusCode, Message;
    private CertificatesData Data;

    public CertificatesNavTreeResponse() {

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

    public CertificatesData getData() {
        return Data;
    }

    public void setData(CertificatesData data) {
        Data = data;
    }
}

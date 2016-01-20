package com.tas.speech.sections.certificates.models;

import java.io.Serializable;

/**
 * Created by drago_000 on 03/08/2015.
 */
public class CertificateSubcategory implements Serializable {

    private String Id, Name;

    public CertificateSubcategory() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}

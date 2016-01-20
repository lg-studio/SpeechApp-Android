package com.tas.speech.sections.certificates.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by drago_000 on 03/08/2015.
 */
public class Certificate implements Serializable {

    private String Id, Name;
    private ArrayList<CertificateSubcategory> Subcategories;

    public Certificate() {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<CertificateSubcategory> getSubcategories() {
        return Subcategories;
    }

    public void setSubcategories(ArrayList<CertificateSubcategory> subcategories) {
        Subcategories = subcategories;
    }
}

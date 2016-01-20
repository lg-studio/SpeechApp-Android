package com.tas.speech.sections.certificates.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by drago_000 on 03/08/2015.
 */
public class CertificatesData implements Serializable {

    private ArrayList<Certificate> Certificates;

    public CertificatesData() {

    }

    public ArrayList<Certificate> getCertificates() {
        return Certificates;
    }

    public void setCertificates(ArrayList<Certificate> certificates) {
        Certificates = certificates;
    }
}

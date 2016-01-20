package com.tas.speech.sections.certificates.client;

import com.tas.speech.sections.certificates.models.CertificatesNavTreeResponse;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by drago_000 on 03/08/2015.
 */
public interface CertificatesService {

    @GET("/CertificatesNavigationTree")
    void certificatesNavigationTree(Callback<CertificatesNavTreeResponse> certNavTreeCallback);
}

package com.tas.speech.sections.certificates.client;

import com.tas.speech.SpeechApp;
import com.tas.speech.sections.certificates.models.CertificatesNavTreeResponse;
import com.tas.speech.client.RetrofitApi;

import retrofit.Callback;
import retrofit.RestAdapter;

public class CertificatesApi extends RetrofitApi {

    private CertificatesService service;

    public CertificatesApi() {
        RestAdapter restAdapter = SpeechApp.getRestAdapter();
        service = restAdapter.create(CertificatesService.class);
    }

    public void certificatesNavigationTree(Callback<CertificatesNavTreeResponse> certNavTreeCallback) {
        service.certificatesNavigationTree(certNavTreeCallback);
    }
}

package com.tas.speech.sections.certificates.client;

import android.content.Context;

import com.tas.speech.utils.DataSources;

import java.io.IOException;
import java.util.Collections;

import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by drago_000 on 03/08/2015.
 */
public class CertificatesMockClient {

    public static Response execute(Request request, Context ctx) throws IOException {

        String responseString = DataSources.getJSONStr(ctx,
                "mocks/certificates/CertificatesNavigationTree.json");

        return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST,
                new TypedByteArray("application/json", responseString.getBytes()));
    }
}

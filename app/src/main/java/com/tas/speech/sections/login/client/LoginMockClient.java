package com.tas.speech.sections.login.client;

import android.content.Context;

import com.tas.speech.utils.DataSources;

import java.io.IOException;
import java.util.Collections;

import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

// Login Mock Client - returns an assets stored json response
public class LoginMockClient {

    public static Response execute(Request request, Context ctx) throws IOException {

        String responseString = DataSources.getJSONStr(ctx, "mocks/login/Login.json");

        return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST,
                new TypedByteArray("application/json", responseString.getBytes()));
    }
}

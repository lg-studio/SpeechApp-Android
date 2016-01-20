package com.tas.speech.client;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit.client.Request;
import retrofit.client.UrlConnectionClient;

public class SpeechClient extends UrlConnectionClient {
    @Override
    protected HttpURLConnection openConnection(Request request) throws IOException {

        HttpURLConnection connection = super.openConnection(request);
        connection.setConnectTimeout(900 * 1000);

        return connection;
    }
}

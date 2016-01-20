package com.tas.speech.client;

import android.content.Context;

import com.tas.speech.sections.certificates.client.CertificatesMockClient;
import com.tas.speech.sections.game.client.GameMockClient;
import com.tas.speech.sections.login.client.LoginMockClient;
import com.tas.speech.sections.tasks.client.MultipleChoiceMockClient;
import com.tas.speech.sections.topic.client.TopicsMockClient;

import java.io.IOException;

import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

public class SpeechMockClient implements Client {

    protected Context mCtx;
    protected long mReponseTime;

    public SpeechMockClient(Context ctx, long responseTime) {
        mCtx = ctx;
        mReponseTime = responseTime;
    }

    @Override
    public Response execute(Request request) throws IOException {

        try {
            Thread.sleep(mReponseTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String url = request.getUrl();
        if(url.startsWith("/Login") || url.startsWith("Logout")) {
            return LoginMockClient.execute(request, mCtx);
        }
        else if(url.startsWith("/CertificatesNavigationTree")) {
            return CertificatesMockClient.execute(request, mCtx);
        }
        else if(url.startsWith("/TaskStructure") || url.equals("/NodeStructure") || url.equals("/TaskInput")) {
            return GameMockClient.execute(request, mCtx);
        }
        else if(url.startsWith("/MultipleChoiceOptions")) {
            return MultipleChoiceMockClient.execute(request, mCtx);
        }
        else if(url.equals("/TaskNavigationTree")) {
            return TopicsMockClient.execute(request, mCtx);
        }

        return null;
    }
}

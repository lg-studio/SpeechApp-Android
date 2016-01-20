package com.tas.speech.sections.tasks.client;

import android.content.Context;
import android.net.Uri;

import com.tas.speech.utils.DataSources;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class MultipleChoiceMockClient {

    public static Response execute(Request request, Context ctx) throws IOException {

        Uri uri = Uri.parse(request.getUrl());
        List<String> pathSegments = uri.getPathSegments();

        String responseString = "DEFAULT_RESPONSE";

        // If a MultipleChoiceOptions request was made, return the assets stored json associated with
        // the received task id
        if(pathSegments.get(0).equals("MultipleChoiceOptions")) {
            if(pathSegments.size() > 1 && pathSegments.get(1).equals("552d077f00c97637a01bbbbb")) {
                responseString = DataSources.getJSONStr(ctx,
                        "mocks/chat/choose_topic/MultipleChoiceOptions_552d077f00c97637a01bbbbb.json");
            }
            else if(pathSegments.size() > 1 && pathSegments.get(1).equals("552d077f00c97637a01ccccc")) {
                responseString = DataSources.getJSONStr(ctx,
                        "mocks/chat/reverse_draw/MultipleChoiceOptions_552d077f00c97637a01ccccc.json");
            }
        }

        return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST,
                new TypedByteArray("application/json", responseString.getBytes()));

    }
}

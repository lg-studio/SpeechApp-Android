package com.tas.speech.sections.topic.client;

import android.content.Context;

import com.tas.speech.utils.DataSources;

import java.io.IOException;
import java.util.Collections;

import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class TopicsMockClient {

    public static Response execute(Request request, Context ctx) throws IOException {

        String responseString = DataSources.getJSONStr(ctx, "mocks/topic_list/TaskNavigationTree_1234_.json");

        return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST,
                new TypedByteArray("application/json", responseString.getBytes()));
    }
}
package com.tas.speech.sections.game.client;

import android.content.Context;
import android.net.Uri;
import android.util.Log;


import com.tas.speech.utils.DataSources;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class GameMockClient {

    public static Response execute(Request request, Context ctx) throws IOException {

        Uri uri = Uri.parse(request.getUrl());
        List<String> pathSegments = uri.getPathSegments();

        Log.d("MOCK SERVER", "fetching uri: " + uri.toString());

        String responseString = "DEFAULT_RESPONSE";

        if(pathSegments.get(0).equals("TaskStructure")) {
            /**
             * Interview
             */
            if(pathSegments.size() > 1 && pathSegments.get(1).equals("552d077f00c97637a01c120b")) {
                responseString = DataSources.getJSONStr(ctx,
                        "mocks/chat/interview/TaskStructure_552d077f00c97637a01c120b.json");
            }
            /**
             * Choose Topic
             */
            else if(pathSegments.size() > 1 && pathSegments.get(1).equals("552d077f00c97637a01bbbbb")) {
                responseString = DataSources.getJSONStr(ctx,
                        "mocks/chat/choose_topic/MultipleChoiceTaskStructure_552d077f00c97637a01bbbbb_1_2.json");
            }
            /**
             * Reverse Draw
             */
            else if(pathSegments.size() > 1 && pathSegments.get(1).equals("552d077f00c97637a01ccccc")) {
                responseString = DataSources.getJSONStr(ctx,
                        "mocks/chat/reverse_draw/MultipleChoiceTaskStructure_552d077f00c97637a01ccccc_1_.json");
            }
        }
        else if(uri.getPath().startsWith("/NodeStructure")) {
            /**
             * Interview
             */
            if(pathSegments.size() > 1 && pathSegments.get(1).equals("552d077f00c97637a01c120b")) {
                if(pathSegments.size() > 2) {
                    if(pathSegments.get(2).equals("1")){
                        responseString = DataSources.getJSONStr(ctx,
                                "mocks/chat/interview/NodeStructure_552d077f00c97637a01c120b_1.json");
                    }
                    else if(pathSegments.get(2).equals("2")) {
                        responseString = DataSources.getJSONStr(ctx,
                                "mocks/chat/interview/NodeStructure_552d077f00c97637a01c120b_2.json");
                    }
                }
            }
            /**
             * Choose Topic
             */
            else if(pathSegments.size() > 1 && pathSegments.get(1).equals("552d077f00c97637a01bbbbb")) {
                if(pathSegments.size() > 2) {
                    if(pathSegments.get(2).equals("1")){
                        responseString = DataSources.getJSONStr(ctx,
                                "mocks/chat/choose_topic/NodeStructure_552d077f00c97637a01bbbbb_1.json");
                    }
                }
            }
            /**
             * Reverse Draw
             */
            else if(pathSegments.size() > 1 && pathSegments.get(1).equals("552d077f00c97637a01ccccc")) {
                if(pathSegments.size() > 2) {
                    if(pathSegments.get(2).equals("1")){

                        responseString = DataSources.getJSONStr(ctx,
                                "mocks/chat/reverse_draw/NodeStructure_552d077f00c97637a01ccccc_1.json");

                    }
                }
            }
        }

        return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST,
                new TypedByteArray("application/json", responseString.getBytes()));
    }
}

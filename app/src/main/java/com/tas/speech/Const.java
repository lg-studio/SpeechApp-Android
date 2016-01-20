package com.tas.speech;

public class Const {

//    public static final String REST_ENDPOINT = "http://86.123.141.37:1337";
    public static final String REST_ENDPOINT = "https://leuphanaspeechapp.herokuapp.com";

    // Mock flag - if true return local stored jsons as responses; if false send requests to the server
    public static final boolean MOCK = false;

    // Mock services response time in milliseconds
    public static final long MOCK_RESPONSE_TIME = 150;

    // Prompt displaying delay in the game screen
    public static final long PROMPT_DELAY = 800;
}

package com.tas.speech.sections.topic.models;

import java.io.Serializable;

/**
 * Created by drago_000 on 03/08/2015.
 */
public class TaskProperties implements Serializable{

    private String Retries, AverageRating;

    public TaskProperties() {

    }

    public String getRetries() {
        return Retries;
    }

    public void setRetries(String retries) {
        Retries = retries;
    }

    public String getAverageRating() {
        return AverageRating;
    }

    public void setAverageRating(String averageRating) {
        AverageRating = averageRating;
    }
}

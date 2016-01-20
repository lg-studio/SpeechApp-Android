package com.tas.speech.sections.topic.models;

import java.io.Serializable;

/**
 * Created by drago_000 on 03/08/2015.
 */
public class TopicProperties implements Serializable {

    private String AverageScore, MinutesSpent;

    public TopicProperties() {

    }

    public String getAverageScore() {
        return AverageScore;
    }

    public void setAverageScore(String averageScore) {
        AverageScore = averageScore;
    }

    public String getMinutesSpent() {
        return MinutesSpent;
    }

    public void setMinutesSpent(String minutesSpent) {
        MinutesSpent = minutesSpent;
    }
}

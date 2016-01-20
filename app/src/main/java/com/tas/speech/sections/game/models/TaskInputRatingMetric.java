package com.tas.speech.sections.game.models;

/**
 * Created by drago on 12/08/2015.
 */
public class TaskInputRatingMetric {

    private String Id, Type;

    private double Rating;

    public TaskInputRatingMetric() {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }
}

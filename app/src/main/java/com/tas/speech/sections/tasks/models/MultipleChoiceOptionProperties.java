package com.tas.speech.sections.tasks.models;

import java.io.Serializable;

/**
 * Created by drago_000 on 05/08/2015.
 */
public class MultipleChoiceOptionProperties implements Serializable {

    private String Text, ImageUrl;

    public MultipleChoiceOptionProperties() {

    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}

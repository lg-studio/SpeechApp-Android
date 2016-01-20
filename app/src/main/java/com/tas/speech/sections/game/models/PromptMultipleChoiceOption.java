package com.tas.speech.sections.game.models;

/**
 * Created by drago_000 on 07/08/2015.
 */
public class PromptMultipleChoiceOption {

    private String Id, Text, Value, PictureUrl;

    public PromptMultipleChoiceOption() {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getPictureUrl() {
        return PictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        PictureUrl = pictureUrl;
    }
}

package com.tas.speech.sections.tasks.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by drago_000 on 05/08/2015.
 */
public class MultipleChoiceOption implements Serializable {

    private String Id, Type;
    private MultipleChoiceOptionProperties Properties;
    private ArrayList<MultipleChoiceOption> InnerOptions;

    public MultipleChoiceOption() {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public MultipleChoiceOptionProperties getProperties() {
        return Properties;
    }

    public void setProperties(MultipleChoiceOptionProperties properties) {
        Properties = properties;
    }

    public ArrayList<MultipleChoiceOption> getInnerOptions() {
        return InnerOptions;
    }

    public void setInnerOptions(ArrayList<MultipleChoiceOption> innerOptions) {
        InnerOptions = innerOptions;
    }
}

package com.tas.speech.sections.tasks.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by drago_000 on 07/08/2015.
 */
public class MultipleChoiceOptions implements Serializable {

    private String DisplayText;
    private ArrayList<MultipleChoiceOption> Options;

    public MultipleChoiceOptions() {

    }

    public ArrayList<MultipleChoiceOption> getOptions() {
        return Options;
    }

    public void setOptions(ArrayList<MultipleChoiceOption> options) {
        Options = options;
    }

    public String getDisplayText() {
        return DisplayText;
    }

    public void setDisplayText(String displayText) {
        DisplayText = displayText;
    }
}

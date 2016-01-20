package com.tas.speech.sections.tasks.models;

import java.io.Serializable;

/**
 * Created by drago_000 on 05/08/2015.
 */
public class MultipleChoiceOptionsData implements Serializable {

    private MultipleChoiceOptions MultipleChoiceOptions;

    public MultipleChoiceOptionsData() {

    }

    public com.tas.speech.sections.tasks.models.MultipleChoiceOptions getMultipleChoiceOptions() {
        return MultipleChoiceOptions;
    }

    public void setMultipleChoiceOptions(com.tas.speech.sections.tasks.models.MultipleChoiceOptions multipleChoiceOptions) {
        MultipleChoiceOptions = multipleChoiceOptions;
    }
}

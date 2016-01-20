package com.tas.speech.form;

import com.tas.speech.form.elements.FormElement;

public class FormElementModel {

    private FormElement.DataType dataType;
    private FormElement.InputType inputType;
    private FormElement.Validation[] validations;
    private FormElement.Property[] properties;

    private String[] values;
    private int defaultValueIndex;

    private String key;
    private String pairKey;

    private int labelRes, hintRes, lovTitleRes;

    public FormElementModel(FormElement.DataType dataType, FormElement.InputType inputType,
                            FormElement.Validation[] validations, FormElement.Property[] properties,
                            String[] values, int defaultValueIndex, int labelRes, int hintRes, String key, int lovTitleRes) {
        this.dataType = dataType;
        this.inputType = inputType;
        this.validations = validations;
        this.properties = properties;
        this.values = values;
        this.defaultValueIndex = defaultValueIndex;
        this.labelRes = labelRes;
        this.hintRes = hintRes;
        this.key = key;
        this.lovTitleRes = lovTitleRes;
    }

    public FormElementModel(FormElement.DataType dataType, FormElement.InputType inputType,
                            FormElement.Validation[] validations, FormElement.Property[] properties,
                            int labelRes, int hintRes, String key) {
        this.dataType = dataType;
        this.inputType = inputType;
        this.validations = validations;
        this.properties = properties;
        this.labelRes = labelRes;
        this.hintRes = hintRes;
        this.key = key;
        this.defaultValueIndex = -1;
    }

    public FormElementModel(FormElement.DataType dataType, FormElement.InputType inputType,
                            FormElement.Validation[] validations, FormElement.Property[] properties,
                            int labelRes, int hintRes, String key, String pairKey) {
        this.dataType = dataType;
        this.inputType = inputType;
        this.validations = validations;
        this.properties = properties;
        this.labelRes = labelRes;
        this.hintRes = hintRes;
        this.key = key;
        this.pairKey = pairKey;
        this.defaultValueIndex = -1;
    }

    public String getKey() {
        return key;
    }

    public String getPairKey() {
        return pairKey;
    }

    public FormElement.InputType getInputType() {
        return inputType;
    }

    public FormElement.Validation[] getValidations() {
        return validations;
    }

    public String[] getValues() {
        return values;
    }

    public int getLabelRes() {
        return labelRes;
    }

    public int getHintRes() {
        return hintRes;
    }

    public int getLovTitle() {
        return lovTitleRes;
    }

    public FormElement.Property[] getProperties() {
        return properties;
    }

    public void setProperties(FormElement.Property[] properties) {
        this.properties = properties;
    }

    public int getDefaultValueIndex() {
        return defaultValueIndex;
    }

    public boolean hasProperty(FormElement.Property testProperty) {
        for(FormElement.Property property : properties) {
            if(testProperty == property) {
                return true;
            }
        }
        return false;
    }
}

package com.tas.speech.form.elements;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tas.speech.form.FormElementModel;
import com.tas.speech.form.FormValidator;
import com.tas.speech.form.validators.EmailValidator;
import com.tas.speech.form.validators.MandatoryValidator;

public class FormElement {
    public enum DataType {
        TEXT, NUMBER;
    }

    public enum InputType {
        LOV, SIMPLE, PASSWORD;
    }

    public enum Validation {
        EMAIL, MANDATORY;
    }

    public enum Property {
        START_WITH_CAP
    }

    protected FormElementModel mElementModel;   // The Model class that contains all the form element's properties
    protected String value; // The value of the form input element

    protected TextView tvLabel; // The TextView corresponding to the form element's label

    public View elementView; // The form element's view (containing the entire layout: label + input)

    protected Context mCtx; // Used to retrieve the layout inflater and all the necessary resources

    public FormElement(Context ctx, FormElementModel elementModel) {
        mElementModel = elementModel;
        value = ""; // initializing the value field with an empty string
        mCtx = ctx;
    }

    // Method
    public void prepareElement(ViewGroup formContainer) {

    }

    public void displayElement(ViewGroup formContainer) {

    }

    public boolean validate() {
        Validation[] validations = mElementModel.getValidations();

        if(validations == null) {
            return true;
        }

        FormValidator validator = null;
        for(Validation validation : validations) {
            switch (validation) {
                case EMAIL:
                    validator = new EmailValidator();
                    break;
                case MANDATORY:
                    validator = new MandatoryValidator();
                    break;
            }
            if(validator != null) {
                boolean valid = validator.validate(value);
                if(!valid) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getValue() {
        return value;
    }

    public FormElementModel getElementModel() {
        return mElementModel;
    }
}

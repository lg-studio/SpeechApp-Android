package com.tas.speech.form;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.tas.speech.form.elements.FormElement;
import com.tas.speech.form.elements.simple.PasswordFormElement;

import java.util.LinkedHashMap;
import java.util.Map;

public class Form {

    private static final String TAG = "Form";

    private Context mCtx;
    private ViewGroup mFormContainer;
    private FormElementModel[] mFormElementModels;
    private LinkedHashMap<String, FormElement> mFormElementsMap;

    public View firstInvalidElement;

    public Form(Context ctx, ViewGroup formContainer, FormElementModel[] formElementModels) {
        mCtx = ctx;
        mFormContainer = formContainer;
        mFormElementModels = formElementModels;
        mFormElementsMap = new LinkedHashMap<>();
    }

    public void render() {
        for(FormElementModel elementModel : mFormElementModels) {
            FormElement formElement = FormElementFactory.getFormElement(mCtx, elementModel);
            formElement.prepareElement(mFormContainer);
            mFormElementsMap.put(elementModel.getKey(), formElement);
        }

        for(Map.Entry<String, FormElement> formElementMapEntry : mFormElementsMap.entrySet()) {
            FormElement value = formElementMapEntry.getValue();
            if(value instanceof PasswordFormElement) {
                ((PasswordFormElement)value).setPairPasswordFormElement((PasswordFormElement)
                        mFormElementsMap.get(value.getElementModel().getPairKey()));
            }
        }

        for(Map.Entry<String, FormElement> formElementMapEntry : mFormElementsMap.entrySet()) {
            formElementMapEntry.getValue().displayElement(mFormContainer);
        }
    }

    public boolean triggerValidations() {
        boolean valid = true;
        firstInvalidElement = null;

        for(Map.Entry<String, FormElement> formElementMapEntry : mFormElementsMap.entrySet()) {
            FormElement formElement = formElementMapEntry.getValue();
            boolean validElement = formElement.validate();
            if(!validElement) {
                firstInvalidElement = formElement.elementView;
                valid = false;
            }
        }

        return valid;
    }

    public LinkedHashMap<String, FormElement> getFormElementsMap() {
        return mFormElementsMap;
    }
}

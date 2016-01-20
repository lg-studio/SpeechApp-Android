package com.tas.speech.form;

import android.content.Context;

import com.tas.speech.form.elements.FormElement;
import com.tas.speech.form.elements.lov.LovFormElement;
import com.tas.speech.form.elements.simple.PasswordFormElement;
import com.tas.speech.form.elements.simple.SimpleFormElement;

public class FormElementFactory {

    public static FormElement getFormElement(Context ctx, FormElementModel formElementModel) {
        if(formElementModel.getInputType() == FormElement.InputType.SIMPLE) {
            return new SimpleFormElement(ctx, formElementModel);
        }
        else if(formElementModel.getInputType() == FormElement.InputType.LOV) {
            return new LovFormElement(ctx, formElementModel);
        }
        else if(formElementModel.getInputType() == FormElement.InputType.PASSWORD) {
            return new PasswordFormElement(ctx, formElementModel);
        }
        return null;
    }
}

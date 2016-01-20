package com.tas.speech.form.validators;

import com.tas.speech.form.FormValidator;

public class MandatoryValidator implements FormValidator{

    public MandatoryValidator() {

    }

    @Override
    public boolean validate(String value) {
        if(value == null || value.trim().equals("")) {
            return false;
        }
        return true;
    }
}

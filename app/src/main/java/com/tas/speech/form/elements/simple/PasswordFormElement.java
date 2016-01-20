package com.tas.speech.form.elements.simple;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;

import com.tas.speech.form.FormElementModel;


public class PasswordFormElement extends SimpleFormElement{

    private static final String TAG = "PasswordFormElement";

    private PasswordFormElement pairPasswordFormElement;

    public PasswordFormElement(Context ctx, FormElementModel elementModel) {
        super(ctx, elementModel);
    }

    public void setPairPasswordFormElement(PasswordFormElement pairPasswordFormElement) {
        this.pairPasswordFormElement = pairPasswordFormElement;
    }

    protected void setElementProperties() {
        etInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public void displayElement(ViewGroup formContainer) {
        etInput.addTextChangedListener(new PasswordTextWatcher(this, this.pairPasswordFormElement));
        formContainer.addView(elementView, 0);
    }

    public boolean validate() {
        validationTriggered = true;

        boolean elementValid = super.validate();
        if(!elementValid) {
            invalidateElement();
        }
        else {
            validateElement();
        }

        return elementValid;

    }

    private class PasswordTextWatcher implements TextWatcher {

        private PasswordFormElement passwordFormElement, pairPasswordFormElement;

        public PasswordTextWatcher(PasswordFormElement passwordFormElement,
                                   PasswordFormElement pairPasswordFormElement) {

            this.passwordFormElement = passwordFormElement;
            this.pairPasswordFormElement = pairPasswordFormElement;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            passwordFormElement.value = s.toString();

            if(validationTriggered) {

                // Go through common validations
                boolean valid = passwordFormElement.validate();

                if (!valid) {
                    passwordFormElement.invalidateElement();
                    this.pairPasswordFormElement.invalidateElement();
                    return;
                }

                // Go through password validation
                if (passwordFormElement.value.equals(this.pairPasswordFormElement.value)) {

                    passwordFormElement.validateElement();
                    pairPasswordFormElement.validateElement();
                } else {

                    passwordFormElement.invalidateElement();
                    pairPasswordFormElement.invalidateElement();
                }
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }
}

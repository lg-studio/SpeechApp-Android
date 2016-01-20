package com.tas.speech.form.elements.simple;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.tas.speech.R;
import com.tas.speech.form.FormElementModel;
import com.tas.speech.form.elements.FormElement;

public class SimpleFormElement extends FormElement {

    private static final String TAG = "SimpleFormElement";

    protected EditText etInput;

    protected boolean validationTriggered;

    public SimpleFormElement(Context ctx, FormElementModel elementModel) {
        super(ctx, elementModel);

        validationTriggered = false;
    }

    protected View inflateElement(ViewGroup formContainer) {
        LayoutInflater layoutInflater = (LayoutInflater)
                mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View simpleElementView = layoutInflater.inflate(R.layout.layout_form_simple,
                formContainer, false);

        tvLabel = (TextView)simpleElementView.findViewById(R.id.tv_form_simple_label);
        etInput = (EditText)simpleElementView.findViewById(R.id.et_form_simple_value);

        return simpleElementView;
    }

    protected void setElementProperties() {
        if(mElementModel.hasProperty(Property.START_WITH_CAP)) {
            etInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                    android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        }
    }

    public void prepareElement(ViewGroup formContainer) {
        elementView = inflateElement(formContainer);

        setElementProperties();

        tvLabel.setText(Html.fromHtml(mCtx.getString(mElementModel.getLabelRes())));
        etInput.setHint(mCtx.getString(mElementModel.getHintRes()));
    }

    public void displayElement(ViewGroup formContainer) {
        etInput.addTextChangedListener(new SimpleTextWatcher(this));
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

    public void invalidateElement() {
        etInput.setBackgroundDrawable(mCtx.getResources()
                .getDrawable(R.drawable.round_rectangle_form_edittext_invalid));
        tvLabel.setTextColor(mCtx.getResources().getColor(R.color.red));
    }

    public void validateElement() {
        etInput.setBackgroundDrawable(mCtx.getResources()
                .getDrawable(R.drawable.round_rectangle_form_edittext_valid));
        tvLabel.setTextColor(mCtx.getResources().getColor(R.color.green_dark));
    }

    private class SimpleTextWatcher implements TextWatcher {

        private SimpleFormElement mFormElement;

        public SimpleTextWatcher(SimpleFormElement formElement) {
            mFormElement = formElement;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mFormElement.value = s.toString();

            if(validationTriggered) {
                boolean valid = mFormElement.validate();
                if (valid) {
                    validateElement();
                } else {
                    invalidateElement();
                }
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }
}

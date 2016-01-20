package com.tas.speech.form.validators;

import com.tas.speech.form.FormValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements FormValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public boolean validate(String value) {
        matcher = pattern.matcher(value);
        return matcher.matches();
    }
}

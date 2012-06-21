package com.mtt.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Validate that the string does not contain HTML.
 */
public final class NotHtmlValidator implements ConstraintValidator<NotHtml, String> {

    @Override
    public void initialize(NotHtml parameters) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null) {
            return !Pattern.compile(".*\\<[^>]+>.*").matcher(value).find();
        }
        return true;
    }
}

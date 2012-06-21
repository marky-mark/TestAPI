package com.mtt.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validate that a string does not contain HTML.
 */
@Documented
@Constraint(validatedBy = NotHtmlValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface NotHtml {

    /**
     * return message
     */
    String message() default "Must not contain HTML";

    /**
     * return groups
     */
    Class<?>[] groups() default { };

    /**
     * return payload
     */
    Class<? extends Payload>[] payload() default { };
}

package com.mtt.api.exception;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ValidationException extends RuntimeException {

    private Set<? extends ConstraintViolation<?>> violations;

    public ValidationException(Set<? extends ConstraintViolation<?>> violations) {
        this.violations = violations;
    }

    public Set<? extends ConstraintViolation<?>> getViolations() {
        return violations;
    }
}

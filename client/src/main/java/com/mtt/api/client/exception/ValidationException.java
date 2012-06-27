package com.mtt.api.client.exception;

import com.mtt.api.model.error.ApiError;

import java.util.List;

public class ValidationException extends RuntimeException {

    private List<ApiError> errors;

    /**
     * Constructor.
     *
     * @param errors the validation errors.
     */
    public ValidationException(List<ApiError> errors) {
        this.errors = errors;
    }

    public List<ApiError> getErrors() {
        return errors;
    }
}

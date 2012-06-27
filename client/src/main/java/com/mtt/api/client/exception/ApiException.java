package com.mtt.api.client.exception;


import com.mtt.api.model.error.ApiErrors;

public class ApiException extends RuntimeException {

    private ApiErrors errors;

    private int statusCode;

    /**
     * Constructor.
     *
     * @param statusCode the status code
     */
    public ApiException(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Constructor.
     *
     * @param statusCode the status code
     * @param errors     the errors
     */
    public ApiException(int statusCode, ApiErrors errors) {
        this.statusCode = statusCode;
        this.errors = errors;
    }

    public ApiErrors getErrors() {
        return errors;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

package com.mtt.api.client.impl;

import com.mtt.api.client.exception.ApiException;
import com.mtt.api.client.exception.ValidationException;
import com.mtt.api.model.error.ApiErrorCode;
import com.mtt.api.model.error.ApiErrors;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ClientResponseFailure;
import org.jboss.resteasy.client.core.BaseClientResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * Helper class for translating RESTEasy client API errors into more specific API exceptions.
 */
public final class ApiExceptionTranslator implements ExceptionTranslator {

    @Override
    public RuntimeException translateResponseFailure(ClientResponseFailure failure) {
        return translate(failure);
    }

    /**
     * Translate the {@link org.jboss.resteasy.client.ClientResponseFailure}.
     *
     * @param failure the {@link org.jboss.resteasy.client.ClientResponseFailure} to translate.
     */
    public static RuntimeException translate(ClientResponseFailure failure) {
        try {
            BaseClientResponse r = (BaseClientResponse) failure.getResponse();
            int statusCode = r.getStatus();
            InputStream stream = r.getStreamFactory().getInputStream();
            if (stream != null) {
                stream.reset();
                ApiErrors errors = convertBodyToApiErrors(r);
                if (errors != null) {
                    if (isValidationError(errors)) {
                        return new ValidationException(errors.getErrors());
                    }
                    return new ApiException(statusCode, errors);
                }
            }
            return new ApiException(statusCode);
        } catch (IOException ex) {
            return failure;
        }
    }

    private static ApiErrors convertBodyToApiErrors(ClientResponse<?> response) {
        try {
            return response.getEntity(ApiErrors.class);
        } catch (RuntimeException ex) {
            return null;
        }
    }

    private static boolean isValidationError(ApiErrors errors) {
        return ApiErrorCode.VALIDATION_FAILED.equals(errors.getErrorCode());
    }
}

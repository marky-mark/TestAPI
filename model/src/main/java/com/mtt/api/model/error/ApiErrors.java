package com.mtt.api.model.error;

import com.mtt.api.model.json.ApiErrorCodeDeserialiser;
import com.mtt.api.model.json.ApiErrorCodeSerialiser;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for error response from Bushfire API.
 */
public final class ApiErrors {

    private ApiErrorCode errorCode;

    private List<ApiError> errors;

    /**
     * @return all errors
     */
    public List<ApiError> getErrors() {
        return errors;
    }

    /**
     * Set errors.
     *
     * @param errors the errors to set
     */
    public void setErrors(List<ApiError> errors) {
        this.errors = errors;
    }

    /**
     * @return the error code
     */
    @JsonSerialize(using = ApiErrorCodeSerialiser.class)
    public ApiErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Set the error code.
     *
     * @param errorCode the error code to set
     */
    @JsonDeserialize(using = ApiErrorCodeDeserialiser.class)
    public void setErrorCode(ApiErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Return true if messagecode exists in list of errors
     * @param messageCode - messagecode to check
     * @return true or false
     */
    public boolean hasMessageCode(String messageCode) {
        Assert.notNull(messageCode);

        if (errors != null) {
            for (ApiError error : errors) {
                if (messageCode.equals(error.getMessageCode())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Builder for {@link ApiErrors}
     * <p/>
     * TODO: Unit tests
     */
    public static final class Builder {

        private ApiErrorCode errorCode;

        private List<ApiError> errors = new ArrayList<ApiError>();

        /**
         * Set error code
         * @param errorCode - error code
         * @return this instance
         */
        public Builder errorCode(ApiErrorCode errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        /**
         * Set error
         * @param error - error
         * @return this instance
         */
        public Builder error(ApiError error) {
            errors.add(error);
            return this;
        }

        /**
         * @return new populated ApiErrors object
         */
        public ApiErrors build() {
            ApiErrors apiErrors = new ApiErrors();
            apiErrors.setErrorCode(errorCode);
            if (errors.size() > 0) {
                apiErrors.setErrors(errors);
            }
            return apiErrors;
        }
    }
}

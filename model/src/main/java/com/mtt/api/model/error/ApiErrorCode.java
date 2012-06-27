package com.mtt.api.model.error;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * The collection of error types.
 */
public enum ApiErrorCode {

    NOT_FOUND("0A", HttpStatus.NOT_FOUND),
    IMAGE_NOT_FOUND("0B", HttpStatus.NOT_FOUND),
    UNSUPPORTED_OPERATION("1A", HttpStatus.BAD_REQUEST),
    VALIDATION_FAILED("1B", HttpStatus.BAD_REQUEST),
    ILLEGAL_ADVERT_ACCOUNT_MODIFICATION("1C", HttpStatus.BAD_REQUEST),
    ADVERT_EXPIRED("1D", HttpStatus.BAD_REQUEST),
    ADVERT_ALREADY_REMOVED("1E", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("1F", HttpStatus.UNAUTHORIZED),
    UNRECOGNISED_POSTCODE("1G", HttpStatus.BAD_REQUEST),
    ILLEGAL_DATE_DURATION("1H", HttpStatus.BAD_REQUEST),
    MISSING_ARGUMENT("1I", HttpStatus.BAD_REQUEST),
    EXTERNAL_REFERENCE_ALREADY_SET("1J", HttpStatus.BAD_REQUEST),
    IMAGE_EMPTY("1K", HttpStatus.BAD_REQUEST),
    IMAGE_UPLOAD_FAILURE("1N", HttpStatus.INTERNAL_SERVER_ERROR),
    UNRECOGNISED_FIELD("1OM", HttpStatus.BAD_REQUEST),
    SERVER_ERROR("2A", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_ACTIVATION_KEY("2B", HttpStatus.NOT_FOUND),
    EXPIRED_ACTIVATION_KEY("2C", HttpStatus.BAD_REQUEST),
    ALREADY_ACTIVATED("2D", HttpStatus.BAD_REQUEST),
    UNKNOWN_USER_ACCOUNT("5A", HttpStatus.UNAUTHORIZED),
    INCORRECT_CREDENTIALS("5B", HttpStatus.UNAUTHORIZED),
    USER_ACCOUNT_INACTIVE("5C", HttpStatus.UNAUTHORIZED),
    USER_ACCOUNT_SUSPENDED("5D", HttpStatus.UNAUTHORIZED),
    USER_ALREADY_EXISTS("5E", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_FOUND("2E", HttpStatus.BAD_REQUEST),
    PACKAGE_NOT_FOUND("2F", HttpStatus.BAD_REQUEST),
    PACKAGE_END_BEFORE_START("2G", HttpStatus.BAD_REQUEST),
    ADVERT_ALREADY_FEATURED("2H", HttpStatus.BAD_REQUEST),
    POSTCODE_MISSING("2I", HttpStatus.BAD_REQUEST),
    POSTCODE_INVALID("2J", HttpStatus.BAD_REQUEST),
    POSTCODE_UNRECOGNISED("2K", HttpStatus.BAD_REQUEST),
    PAYMENT_URL_FAILURE("6A", HttpStatus.BAD_REQUEST),
    PAYMENT_VALIDATE_FAILURE("6B", HttpStatus.BAD_REQUEST),
    INVALID_RESET_PASSWORD_KEY("7A", HttpStatus.NOT_FOUND),
    EXPIRED_RESET_PASSWORD_KEY("7B", HttpStatus.BAD_REQUEST),
    ENTITY_STALE("8A", HttpStatus.CONFLICT);

    private String errorCode;

    private HttpStatus status;

    private ApiErrorCode(String errorCode, HttpStatus status) {
        this.errorCode = errorCode;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @return string value of this error code
     */
    public String asString() {
        return errorCode;
    }

    /**
     * Get {@link ApiErrorCode} for given error code.
     *
     * @param errorCode the error code
     * @return {@link ApiErrorCode} for given error code.
     */
    public static ApiErrorCode fromString(String errorCode) {
        for (ApiErrorCode errorType : values()) {
            if (errorType.asString().equals(errorCode)) {
                return errorType;
            }
        }
        throw new IllegalArgumentException("Unrecognised error code");
    }
}

package com.mtt.api.model.error;

/**
 * Models a Bushfire API error.
 */
public final class ApiError {

    private String messageCode;

    private String defaultMessage;

    private String field;

    private String[] args;

    public String getField() {
        return field;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public String[] getArgs() {
        return args;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    /**
     * Builder class for {@link ApiError}s.
     */
    public static final class Builder {

        private String messageCode;
        private String defaultMessage;
        private String field;
        private String[] args;

        /**
         * Set message code
         * @param messageCode - message code
         * @return this builder
         */
        public Builder messageCode(String messageCode) {
            this.messageCode = messageCode;
            return this;
        }

        /**
         * Set default message
         * @param defaultMessage - default message
         * @return this builder
         */
        public Builder defaultMessage(String defaultMessage) {
            this.defaultMessage = defaultMessage;
            return this;
        }

        /**
         * Set field
         * @param field - field
         * @return this builder
         */
        public Builder field(String field) {
            this.field = field;
            return this;
        }

        /**
         * Set args
         * @param args - args
         * @return this builder
         */
        public Builder args(String... args) {
            this.args = args;
            return this;
        }

        /**
         * Build ApiError object
         * @return new, populated ApiError
         */
        public ApiError build() {
            ApiError error = new ApiError();
            error.messageCode = messageCode;
            error.defaultMessage = defaultMessage;
            error.field = field;
            error.args = args;
            return error;
        }
    }
}

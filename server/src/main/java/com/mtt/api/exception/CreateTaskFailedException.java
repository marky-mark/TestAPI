package com.mtt.api.exception;

import com.mtt.service.request.CreateTaskRequest;

public class CreateTaskFailedException extends RuntimeException {

    private CreateTaskRequest request;

    public CreateTaskFailedException(RuntimeException cause, CreateTaskRequest request) {
        super(cause);
        this.request = request;
    }

    public CreateTaskRequest getRequest() {
        return request;
    }
}

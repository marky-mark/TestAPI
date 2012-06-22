package com.mtt.api.exception;

import com.mtt.service.request.CreateTaskRequest;

public class UpdateTaskFailedException extends RuntimeException {

    private CreateTaskRequest request;

    public UpdateTaskFailedException(RuntimeException cause, CreateTaskRequest request) {
        super(cause);
        this.request = request;
    }

    public CreateTaskRequest getCreateTaskRequest() {
        return request;
    }
}

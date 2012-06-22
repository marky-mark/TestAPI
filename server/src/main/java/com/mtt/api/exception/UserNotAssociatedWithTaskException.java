package com.mtt.api.exception;

import com.mtt.service.request.CreateTaskRequest;

public class UserNotAssociatedWithTaskException extends RuntimeException {

    private CreateTaskRequest createTaskRequest;

    public UserNotAssociatedWithTaskException(CreateTaskRequest createTaskRequest) {
        this.createTaskRequest = createTaskRequest;
    }

    public CreateTaskRequest getCreateTaskRequest() {
        return createTaskRequest;
    }
}

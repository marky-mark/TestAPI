package com.mtt.domain.exception;

public class TaskNotFoundException extends RuntimeException{

    private Long id;

    public TaskNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

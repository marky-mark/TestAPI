package com.mtt.api.controller;

import com.mtt.api.exception.CreateTaskFailedException;
import com.mtt.api.exception.ValidationException;
import com.mtt.domain.exception.TaskNotFoundException;
import com.mtt.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

public class BaseExceptionHandlingController {

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseBody
    public final String handleTaskNotFoundException(TaskNotFoundException ex, HttpServletResponse response) {
        RuntimeException rx = (RuntimeException) ex.getCause();
        return respond(HttpStatus.NOT_FOUND, response, "task not found");
    }

    @ExceptionHandler(CreateTaskFailedException.class)
    @ResponseBody
    public final String handleCreateTaskFailedException(CreateTaskFailedException ex, HttpServletResponse response) {
        RuntimeException rx = (RuntimeException) ex.getCause();

        if (rx instanceof ValidationException) {
            return respond(HttpStatus.BAD_REQUEST, response, "some violations");
        } else if (rx instanceof UserNotFoundException) {
            return respond(HttpStatus.NOT_FOUND, response, "user is not found");
        }

        return respond(HttpStatus.INTERNAL_SERVER_ERROR, response, "something went wrong");
    }

    private String respond(HttpStatus httpStatus, HttpServletResponse response, String message) {
        response.setStatus(httpStatus.value());
        return message;
    }
}

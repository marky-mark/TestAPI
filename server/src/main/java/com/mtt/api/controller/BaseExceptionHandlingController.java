package com.mtt.api.controller;

import com.mtt.api.model.error.ApiError;
import com.mtt.api.model.error.ApiErrorCode;
import com.mtt.api.model.error.ApiErrors;
import com.mtt.api.exception.CreateTaskFailedException;
import com.mtt.api.exception.UpdateTaskFailedException;
import com.mtt.api.exception.UserNotAssociatedWithTaskException;
import com.mtt.api.exception.ValidationException;
import com.mtt.domain.exception.TaskNotFoundException;
import com.mtt.domain.exception.UserNotFoundException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BaseExceptionHandlingController {

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseBody
    public final String handleTaskNotFoundException(TaskNotFoundException ex, HttpServletResponse response)
        throws Exception {
        RuntimeException rx = (RuntimeException) ex.getCause();
        return respond(ApiErrorCode.NOT_FOUND, response);
    }

    @ExceptionHandler(CreateTaskFailedException.class)
    @ResponseBody
    public final String handleCreateTaskFailedException(CreateTaskFailedException ex, HttpServletResponse response)
        throws Exception {
        RuntimeException rx = (RuntimeException) ex.getCause();

        if (rx instanceof ValidationException) {
            return respond(ApiErrorCode.VALIDATION_FAILED, ((ValidationException)rx).getViolations(), response);
        } else if (rx instanceof UserNotFoundException) {
            return respond(ApiErrorCode.NOT_FOUND, response);
        }

        return respond(ApiErrorCode.SERVER_ERROR, response);
    }

    @ExceptionHandler(UpdateTaskFailedException.class)
    @ResponseBody
    public final String handleUpdateTaskFailedException(UpdateTaskFailedException ex, HttpServletResponse response)
        throws Exception {

        RuntimeException rx = (RuntimeException) ex.getCause();

        if (rx instanceof ValidationException) {
            return respond(ApiErrorCode.VALIDATION_FAILED, ((ValidationException)rx).getViolations(), response);
        } else if (rx instanceof UserNotFoundException) {
            return respond(ApiErrorCode.NOT_FOUND, response);
        } else if (rx instanceof UserNotAssociatedWithTaskException) {
            return respond(ApiErrorCode.UNRECOGNISED_FIELD, response);
        } else if (rx instanceof TaskNotFoundException) {
            return respond(ApiErrorCode.NOT_FOUND, response);
        }

        return respond(ApiErrorCode.SERVER_ERROR, response);
    }

    /**
     * If All the above fail to be caught...this will catch any runtime Exception
     * @param ex
     * @param response
     * @return
     * @throws Exception
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public final String defaultExceptionHandler(Exception ex, HttpServletResponse response) throws Exception {
        RuntimeException rx = (RuntimeException) ex.getCause();
        return respond(ApiErrorCode.SERVER_ERROR, response);
    }

    private String respond(ApiErrorCode apiErrorCode, HttpServletResponse response) throws Exception {
        response.setStatus(apiErrorCode.getStatus().value());

        ApiErrors apiErrors = new ApiErrors();
        apiErrors.setErrorCode(apiErrorCode);

        return objectMapper.writeValueAsString(apiErrors);
    }

    private String respond(ApiErrorCode apiErrorCode,
                           Set<? extends ConstraintViolation<?>> violations,
                           HttpServletResponse response) throws Exception {
        response.setStatus(apiErrorCode.getStatus().value());

        ApiErrors apiErrors = new ApiErrors();
        apiErrors.setErrorCode(apiErrorCode);

        List<ApiError> errors = new ArrayList<ApiError>();

        for (ConstraintViolation violation: violations) {
            ApiError apiError = new ApiError();
            apiError.setMessageCode(violation.getMessage());
            apiError.setField(violation.getPropertyPath().toString());
            errors.add(apiError);
        }

        apiErrors.setErrors(errors);

        return objectMapper.writeValueAsString(apiErrors);
    }
}

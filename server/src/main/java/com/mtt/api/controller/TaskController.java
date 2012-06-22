package com.mtt.api.controller;

import com.mtt.api.exception.CreateTaskFailedException;
import com.mtt.api.exception.UpdateTaskFailedException;
import com.mtt.api.exception.UserNotAssociatedWithTaskException;
import com.mtt.api.exception.ValidationException;
import com.mtt.api.model.APITask;
import com.mtt.domain.entity.Task;
import com.mtt.domain.entity.User;
import com.mtt.service.TaskService;
import com.mtt.service.UserService;
import com.mtt.service.request.CreateTaskRequest;
import com.mtt.service.request.UpdateTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Controller
public class TaskController extends BaseExceptionHandlingController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private Validator validator;

    @RequestMapping(value = { "/task/{id}" }, method = RequestMethod.GET)
    @ResponseBody
    public APITask getTask(@PathVariable("id") Long id) {
        Task dbTask = taskService.find(id);
        return conversionService.convert(dbTask, APITask.class);
    }

    @RequestMapping(value = { "/task" }, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public APITask createTask(@RequestBody CreateTaskRequest createTaskRequest) {

        try {
            Set<ConstraintViolation<CreateTaskRequest>> violations = validator.validate(createTaskRequest);

            if (violations.size() > 0) {
                throw new ValidationException(violations);
            }

            Task dbTask = taskService.create(createTaskRequest);
            return conversionService.convert(dbTask, APITask.class);
        } catch (RuntimeException e) {
            throw new CreateTaskFailedException(e, createTaskRequest);
        }
    }

    @RequestMapping(value = "/task/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public APITask updateTask(@PathVariable("id") String id, @RequestBody CreateTaskRequest createTaskRequest) {
        try {

            //1. Check for validation errors
            Set<ConstraintViolation<CreateTaskRequest>> violations = validator.validate(createTaskRequest);

            if (violations.size() > 0) {
                throw new ValidationException(violations);
            }

            //2. Make sure the task is associated with the user
            User user = userService.find(createTaskRequest.getUserId());

            if (!taskService.find(new Long(id)).getUser().getId().equals(user.getId())) {
                throw new UserNotAssociatedWithTaskException(createTaskRequest);
            }

            //3. Update The task
            UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
            updateTaskRequest.setChecked(createTaskRequest.isChecked());
            updateTaskRequest.setDescription(createTaskRequest.getDescription());
            updateTaskRequest.setTitle(createTaskRequest.getTitle());
            updateTaskRequest.setId(new Long(id));

            Task dbTask = taskService.update(updateTaskRequest);
            return conversionService.convert(dbTask, APITask.class);
        } catch (RuntimeException e) {
            throw new UpdateTaskFailedException(e, createTaskRequest);
        }
    }


    @RequestMapping(value = "/task/delete/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void deleteTask(@PathVariable("id") String id) {
        taskService.delete(new Long(id));
    }
}

package com.mtt.api.controller;

import com.mtt.api.model.APITask;
import com.mtt.domain.entity.Task;
import com.mtt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ConversionService conversionService;

    @RequestMapping(value = { "/task/{id}" }, method = RequestMethod.GET)
    @ResponseBody
    public APITask getTask(@PathVariable("id") Long id) {
        Task dbTask = taskService.find(id);
        return conversionService.convert(dbTask, APITask.class);
    }
}

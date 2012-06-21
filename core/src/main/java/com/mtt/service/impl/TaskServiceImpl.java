package com.mtt.service.impl;

import com.mtt.domain.entity.Task;
import com.mtt.domain.entity.User;
import com.mtt.domain.exception.TaskNotFoundException;
import com.mtt.domain.exception.UserNotFoundException;
import com.mtt.repository.TaskRepository;
import com.mtt.repository.UserRepository;
import com.mtt.service.TaskService;
import com.mtt.service.request.CreateTaskRequest;
import com.mtt.service.request.UpdateTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Service for tasks
 */
@Service
@Transactional
public final class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param id of the task
     * @return the task
     */
    @Override
    public Task find(Long id) {
        Task task = taskRepository.findOne(id);

        if(task != null) {
            return task;
        }

        throw new TaskNotFoundException(id);
    }

    /**
     * find list of task associated with User
     * @param id of the user
     * @return list of tasks
     */
    @Override
    public List<Task> findByUser(Long id) {
        return taskRepository.findByUserId(id);
    }

    @Override
    @Transactional
    public Task delete(Long id) {
        Task task = taskRepository.findOne(id);

        if (task != null) {
            taskRepository.delete(task);
            return task;
        }

        throw new TaskNotFoundException(id);
    }

    /**
     * Create a Task - MUST be associated with a User
     * @param createTaskRequest  request to represent the data
     * @return the task created
     */
    @Override
    @Transactional
    public Task create(CreateTaskRequest createTaskRequest) {

        User user = userRepository.findOne(createTaskRequest.getUserId());

        if (user != null) {
            Task task = new Task();
            task.setTitle(createTaskRequest.getTitle());
            task.setDescription(createTaskRequest.getDescription());
            task.setUser(user);
            task.setCreatedDate(new Date(System.currentTimeMillis()));
            taskRepository.save(task);

            return task;
        }

        throw new UserNotFoundException(createTaskRequest.getUserId());
    }

    @Override
    public Task update(UpdateTaskRequest updateTaskRequest) {

        Task task = taskRepository.findOne(updateTaskRequest.getId());

        if(task != null) {
            task.setDescription(updateTaskRequest.getDescription());
            task.setTitle(updateTaskRequest.getTitle());
            task.setChecked(updateTaskRequest.isChecked());
            taskRepository.save(task);

            return task;
        }

        throw new TaskNotFoundException(updateTaskRequest.getId());

    }
}

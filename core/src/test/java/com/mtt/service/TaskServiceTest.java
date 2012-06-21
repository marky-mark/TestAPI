package com.mtt.service;

import com.mtt.domain.entity.Task;
import com.mtt.domain.entity.TestUtils;
import com.mtt.domain.entity.User;
import com.mtt.domain.exception.TaskNotFoundException;
import com.mtt.domain.exception.UserNotFoundException;
import com.mtt.repository.TaskRepository;
import com.mtt.repository.UserRepository;
import com.mtt.service.impl.TaskServiceImpl;
import com.mtt.service.request.CreateTaskRequest;
import com.mtt.service.request.UpdateTaskRequest;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskServiceTest {

    private TaskService taskService;

    private TaskRepository taskRepository;

    private UserRepository userRepository;

    @Before
    public void init() {
        taskService = new TaskServiceImpl();

        taskRepository = mock(TaskRepository.class);
        userRepository = mock(UserRepository.class);
        ReflectionTestUtils.setField(taskService, "taskRepository", taskRepository);
        ReflectionTestUtils.setField(taskService, "userRepository", userRepository);
    }

    @Test(expected = TaskNotFoundException.class)
    public void taskNotFound() {
        when(taskRepository.findOne(1L)).thenReturn(null);

        taskService.find(1L);
    }

    @Test
    public void taskFound() {
        Task taskToReturn = TestUtils.createTask(1L);
        when(taskRepository.findOne(1L)).thenReturn(taskToReturn);

        Task returnedTask = taskService.find(1L);

        assertThat(returnedTask, equalTo(taskToReturn));
    }

    @Test
    public void testFindListOfTasksByUser() {
        List<Task> tasks = new ArrayList<Task>();
        Task task1 = TestUtils.createTask(1L);
        tasks.add(task1);
        Task task2 = TestUtils.createTask(2L);
        tasks.add(task2);

        when(taskRepository.findByUserId(1L)).thenReturn(tasks);

        List<Task> returnedTasks = taskService.findByUser(1L);

        assertThat(returnedTasks.size(), equalTo(2));
        assertThat(returnedTasks.get(0), equalTo(task1));
        assertThat(returnedTasks.get(1), equalTo(task2));
    }

    @Test(expected = UserNotFoundException.class)
    public void createTaskWithInvalidUser() {
        when(userRepository.findOne(1L)).thenReturn(null);

        CreateTaskRequest request = new CreateTaskRequest();
        request.setDescription("blah");
        request.setTitle("more blah");
        request.setUserId(1L);
        taskService.create(request);
    }

    @Test
    public void testTaskReturnedWithRealUser() {
        User user = TestUtils.createUser(1L);
        when(userRepository.findOne(1L)).thenReturn(user);

        CreateTaskRequest request = new CreateTaskRequest();
        request.setDescription("blah");
        request.setTitle("more blah");
        request.setUserId(1L);

        Task task = taskService.create(request);
        assertThat(task.getTitle(), equalTo("more blah"));
        assertThat(task.getDescription(), equalTo("blah"));
    }

    @Test(expected = TaskNotFoundException.class)
    public void testDeleteWithInvalidId() {
        when(taskRepository.findOne(1L)).thenReturn(null);
        taskService.delete(1L);
    }

    @Test
    public void testDeleteAdWithValidId() {
        Task taskToReturn = TestUtils.createTask(1L);
        when(taskRepository.findOne(1L)).thenReturn(taskToReturn);
        Task returned = taskService.delete(1L);

        assertThat(returned, equalTo(taskToReturn));
    }

    @Test(expected = TaskNotFoundException.class)
    public void testUpdateAdIdNotFound() {
        when(taskRepository.findOne(1L)).thenReturn(null);

        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setDescription("helllo");
        updateTaskRequest.setChecked(true);

        taskService.update(updateTaskRequest);
    }

    @Test
    public void testUpdateTask() {
        Task taskToReturn = TestUtils.createTask(1L);
        User testUser = TestUtils.createUser(1L);
        taskToReturn.setChecked(false);
        taskToReturn.setDescription("Please change");
        taskToReturn.setTitle("Please Change");
        taskToReturn.setUser(testUser);
        DateTime datetime = new DateTime();
        taskToReturn.setCreatedDate(datetime.toDate());
        when(taskRepository.findOne(1L)).thenReturn(taskToReturn);

        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setDescription("hello");
        updateTaskRequest.setChecked(true);
        updateTaskRequest.setTitle("changed");
        updateTaskRequest.setId(1L);

        Task returned = taskService.update(updateTaskRequest);

        assertThat(returned.getUser(), equalTo(testUser));
        assertThat(returned.getCreatedDate(), equalTo(datetime.toDate()));
        assertThat(returned.getTitle(), equalTo("changed"));
        assertThat(returned.isChecked(), equalTo(true));
        assertThat(returned.getDescription(), equalTo("hello"));
    }
}

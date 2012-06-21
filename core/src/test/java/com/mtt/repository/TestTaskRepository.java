package com.mtt.repository;

import com.mtt.domain.entity.Task;
import com.mtt.domain.entity.User;
import com.mtt.test.BaseIntegrationTest;
import com.mtt.test.DataSetLocation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@DataSetLocation("classpath:/com/mtt/datasets/tasks.xml")
public class TestTaskRepository extends BaseIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testTaskCount() {
        assertThat(taskRepository.count(), equalTo(2L));
    }

    @Test
    public void testGetTask() {
        Task task = taskRepository.findOne(1L);
        assertThat(task.getTitle(), equalTo("test1"));
        assertThat(task.getDescription(), equalTo("Test data"));
        assertThat(task.isChecked(), equalTo(false));

        testUser(task.getUser());
    }

    @Test
    public void testUpdateTask() {

        Task task = taskRepository.findOne(2L);
        assertThat(task.getTitle(), equalTo("test2"));
        assertThat(task.getDescription(), equalTo("checked is true"));
        Date createdDate = task.getCreatedDate();

        task.setChecked(true);
        task.setDescription("hello");
        task.setTitle("newTitle");
        taskRepository.save(task);
        taskRepository.flush();

        task = taskRepository.findOne(2L);
        assertThat(task.getTitle(), equalTo("newTitle"));
        assertThat(task.getDescription(), equalTo("hello"));
        assertThat(task.isChecked(), equalTo(true));
        //the created date should NEVER change
        assertThat(task.getCreatedDate(), equalTo(createdDate));

        testUser(task.getUser());
    }

    @Test
    public void testNewTask() {
        Task task = taskRepository.findOne(2L);

        Task newTask = new Task();
        newTask.setChecked(true);
        newTask.setDescription("new Inserted Task");
        newTask.setTitle("New Title");
        newTask.setUser(task.getUser());
        newTask.setCreatedDate(new Date(System.currentTimeMillis()));
        taskRepository.save(newTask);
        taskRepository.flush();

        Task returnedTask = taskRepository.findOne(3L);
        assertThat(returnedTask.getDescription(), equalTo("new Inserted Task"));
        assertThat(returnedTask.getTitle(), equalTo("New Title"));
        assertThat(returnedTask.getUser(), equalTo(task.getUser()));
    }

    @Test
    public void testGetTasksByUser() {
        List<Task> tasks = taskRepository.findByUserId(1L);
        assertThat(tasks.size(), equalTo(2));
    }

    @Test
    public void testDeleteTask() {
        taskRepository.delete(2L);
        taskRepository.flush();
        assertThat(taskRepository.findByUserId(1L).size(), equalTo(1));
    }

    private void testUser(User user) {
        assertThat(user.getId(), equalTo(1L));
        assertThat(user.getUsername(), equalTo("mark"));
        assertThat(user.getPassword(), equalTo("password"));
    }
}

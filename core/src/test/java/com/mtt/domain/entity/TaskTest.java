package com.mtt.domain.entity;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TaskTest {

    @Test
    public void testTaskMatch() {
        Task task1 = TestUtils.createTask(1L);
        DateTime datetime = new DateTime();
        task1.setCreatedDate(datetime.toDate());
        task1.setTitle("task1");
        task1.setDescription("task1 desc");
        task1.setUser(TestUtils.createUser(1L));

        Task task2 = TestUtils.createTask(1L);
        datetime.minusSeconds(100000);
        task2.setCreatedDate(datetime.toDate());
        task2.setTitle("task2");
        task2.setDescription("task2 desc");
        task2.setUser(TestUtils.createUser(2L));

        assertThat(task1.equals(task2), equalTo(true));
    }

    @Test
    public void testTaskMisMatch() {

        Task task1 = TestUtils.createTask(1L);
        DateTime datetime = new DateTime();
        task1.setCreatedDate(datetime.toDate());
        task1.setTitle("task1");
        task1.setDescription("task1 desc");
        task1.setUser(TestUtils.createUser(1L));

        Task task2 = TestUtils.createTask(2L);
        task2.setCreatedDate(datetime.toDate());
        task2.setTitle("task1");
        task2.setDescription("task1 desc");
        task2.setUser(TestUtils.createUser(1L));

        assertThat(task1.equals(task2), equalTo(false));

    }

    @Test
    public void testHashCode() {
        Task task1 = TestUtils.createTask(1L);
        assertThat(task1.hashCode(), equalTo(task1.getId().hashCode()));
    }

}

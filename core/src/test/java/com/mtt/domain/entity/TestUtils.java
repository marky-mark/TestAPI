package com.mtt.domain.entity;

import org.junit.Ignore;
import org.springframework.test.util.ReflectionTestUtils;

@Ignore
public final class TestUtils {

    public static User createUser(Long id) {
        User user = new User();
        ReflectionTestUtils.setField(user, "id", id);
        return user;
    }

    public static Task createTask(Long id) {
        Task task = new Task();
        ReflectionTestUtils.setField(task, "id", id);
        return task;
    }
}

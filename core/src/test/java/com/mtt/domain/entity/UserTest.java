package com.mtt.domain.entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void testEqualWhenIdsMatch() {
        User user1 = TestUtils.createUser(1L);
        user1.setUsername("test1");
        user1.setPassword("test1");
        User user2 = TestUtils.createUser(1L);
        user2.setUsername("test2");
        user2.setPassword("test2");
        assertThat(user1.equals(user2), equalTo(true));
    }

    @Test
    public void testNotEqualWhenIdsNotEqual() {
        User user1 = TestUtils.createUser(1L);
        user1.setUsername("test1");
        user1.setPassword("test1");
        User user2 = TestUtils.createUser(2L);
        user2.setUsername("test1");
        user2.setPassword("test1");
        assertThat(user1.equals(user2), equalTo(false));
    }

    @Test
    public void testHashCode() {
        User user1 = TestUtils.createUser(1L);
        assertThat(user1.hashCode(), equalTo(user1.getId().hashCode()));
    }

    @Test
    public void testToString() {
        User user1 = TestUtils.createUser(1L);
        user1.setUsername("mkelly");
        user1.setPassword("password");
        assertThat(user1.toString(), equalTo("User{id=1, username=\'mkelly\'}"));
    }
}

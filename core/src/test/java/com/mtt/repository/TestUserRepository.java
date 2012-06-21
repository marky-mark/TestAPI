package com.mtt.repository;

import com.mtt.domain.entity.User;
import com.mtt.test.BaseIntegrationTest;
import com.mtt.test.DataSetLocation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@DataSetLocation("classpath:/com/mtt/datasets/users.xml")
public class TestUserRepository extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRepoCount() {
        assertThat(userRepository.count(), equalTo(2L));
    }

    @Test
    public void testGetUser() {
        User user = userRepository.findOne(2L);
        assertThat(user.getUsername(), equalTo("mark"));
        assertThat(user.getPassword(), equalTo("password"));
    }

    @Test
    public void testGetUserByUserName() {
        User user = userRepository.findByUserName("kelly");
        assertThat(user.getId(), equalTo(3L));
        assertThat(user.getUsername(), equalTo("kelly"));
        assertThat(user.getPassword(), equalTo("password2"));
    }

}

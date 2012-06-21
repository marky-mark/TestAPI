package com.mtt.service;

import com.mtt.domain.entity.User;
import com.mtt.service.request.CreateUserRequest;

public interface UserService {

    /**
     *
     * @param id of user
     * @return the user
     */
    User find(Long id);

    /**
     *
     * @param username of user
     * @return the user
     */
    User find(String username);

    /**
     * Used to authenticate at login
     * @param username  the username
     * @param plainTextpassword Plain text password
     * @return the User
     */
    User authenticate(String username, String plainTextpassword);

    User create(CreateUserRequest createUserRequest);
}

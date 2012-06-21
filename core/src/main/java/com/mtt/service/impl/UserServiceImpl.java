package com.mtt.service.impl;

import com.mtt.domain.entity.User;
import com.mtt.domain.entity.UserStatus;
import com.mtt.domain.exception.IncorrectPasswordException;
import com.mtt.domain.exception.UserAlreadyExistsException;
import com.mtt.domain.exception.UserNotActiveException;
import com.mtt.domain.exception.UserNotFoundException;
import com.mtt.repository.UserRepository;
import com.mtt.service.UserService;
import com.mtt.service.request.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to find users in the system
 */
@Transactional
@Service
public final class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User find(Long id) {
        User user = userRepository.findOne(id);

        if (user != null) {
            return user;
        }

        throw new UserNotFoundException(id);
    }

    @Override
    public User find(String username) {
        User user = userRepository.findByUserName(username);

        if (user != null) {
            return user;
        }

        throw new UserNotFoundException(username);
    }

    @Override
    public User authenticate(String username, String plainTextpassword) {
        User user = find(username);

        //make sure the user is active
        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new UserNotActiveException();
        }

        if(user.verifyPassword(plainTextpassword)) {
            return user;
        }

        throw new IncorrectPasswordException();
    }

    @Override
    public User create(CreateUserRequest createUserRequest) {
        if (userRepository.findByUserName(createUserRequest.getUserName()) != null) {
            throw new UserAlreadyExistsException(createUserRequest.getUserName());
        }

        User user = new User();
        user.setUsername(createUserRequest.getUserName());
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setPassword(createUserRequest.getPlainTextPassword());
        user.setTelephoneNumber(createUserRequest.getTelephoneNumber());
        user.setStatus(UserStatus.AWAITING_ACTIVATION);
        userRepository.saveAndFlush(user);
        return user;
    }
}

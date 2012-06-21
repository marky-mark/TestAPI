package com.mtt.repository;

import com.mtt.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find the user by the unique constraint of a username
     * @param username queried user
     * @return User if exists
     */
    @Query("select u from User as u where u.username = :username")
    User findByUserName(@Param("username") String username);
}

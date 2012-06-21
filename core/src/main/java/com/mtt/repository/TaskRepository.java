package com.mtt.repository;

import com.mtt.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find tasks associated with an user
     * @param userId to id the account
     * @return list of all adverts associated with account
     */
    List<Task> findByUserId(@Param("userId") Long userId);
}

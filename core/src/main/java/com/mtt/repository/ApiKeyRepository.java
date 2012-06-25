package com.mtt.repository;

import com.mtt.domain.entity.ApiKey;
import com.mtt.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    List<Task> findByUserId(@Param("userId") Long userId);

}

package com.mtt.repository;

import com.mtt.domain.entity.UserActivationKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivationKeyRepository extends JpaRepository<UserActivationKey, Long> {

    //The Key is indexed - see liquibase, JPA takes care of the search
    //once the passed variable is the same name in UserActivationKey
    UserActivationKey findByKey(String key);
}

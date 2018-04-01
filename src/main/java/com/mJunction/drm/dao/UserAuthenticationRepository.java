package com.mJunction.drm.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mJunction.drm.common.entity.UserAuthentication;

/**
 * Created by siddhartha.kumar on 4/4/2017.
 */
public interface UserAuthenticationRepository extends CrudRepository<UserAuthentication,Integer> {

    @Query(name = "select a from UserAuthentication a where a.userId=:userId")
    public UserAuthentication findOneByUserId(String userId);
}

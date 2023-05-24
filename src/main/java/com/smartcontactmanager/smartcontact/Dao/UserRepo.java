package com.smartcontactmanager.smartcontact.Dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartcontactmanager.smartcontact.entities.UserEntity;

public interface UserRepo extends CrudRepository<UserEntity, Integer> {

    @Query("select u from UserEntity u where u.email=:email")
    public UserEntity getuserEntitybyname(@Param("email") String email);
}

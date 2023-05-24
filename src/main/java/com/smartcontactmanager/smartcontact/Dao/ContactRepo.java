package com.smartcontactmanager.smartcontact.Dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.smartcontactmanager.smartcontact.entities.Contact;
import com.smartcontactmanager.smartcontact.entities.UserEntity;

import jakarta.transaction.Transactional;

public interface ContactRepo extends CrudRepository<Contact, Integer> {

	public Page<Contact> findByUserEntity(UserEntity userEntity, Pageable p);

	public Contact findByCidAndUserEntity(int cid, UserEntity userEntity);

	@Transactional
	public Long deleteByCidAndUserEntity(int cid, UserEntity userEntity);



}

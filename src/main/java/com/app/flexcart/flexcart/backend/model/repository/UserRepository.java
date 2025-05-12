package com.app.flexcart.flexcart.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.flexcart.flexcart.backend.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}

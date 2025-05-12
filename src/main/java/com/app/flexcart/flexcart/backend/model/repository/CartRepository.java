package com.app.flexcart.flexcart.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.flexcart.flexcart.backend.model.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

}

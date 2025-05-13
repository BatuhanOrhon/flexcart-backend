package com.app.flexcart.flexcart.backend.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.flexcart.flexcart.backend.model.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUser_UserIdAndOrderDateAfter(Long userId, LocalDateTime after);

    long countByUser_UserId(Long userId);

    List<OrderEntity> findByUser_UserId(Long userId);

}

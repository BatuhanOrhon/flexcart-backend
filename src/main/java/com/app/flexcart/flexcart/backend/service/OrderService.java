package com.app.flexcart.flexcart.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.app.flexcart.flexcart.backend.model.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public long getOrderCountByUserId(Long userId) {
        return orderRepository.countByUser_UserId(userId);
    }

    public BigDecimal getUserPaymentLastYear(Long userId) {
        var orders = orderRepository.findByUser_UserIdAndOrderDateAfter(userId, LocalDateTime.now().minusYears(1));
        return orders.stream()
                .map(o -> o.getTotal().subtract(o.getDiscount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

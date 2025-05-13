package com.app.flexcart.flexcart.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.IOrderController;
import com.app.flexcart.flexcart.backend.service.OrderService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OrderControllerImpl implements IOrderController {
    private final OrderService orderService;

    @Override
    public ResponseEntity<String> placeOrder(@NotNull Long userId) {
        orderService.placeOrder(userId);
        return ResponseEntity.ok("Order is placed successfully");
    }
}

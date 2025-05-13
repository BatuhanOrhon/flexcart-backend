package com.app.flexcart.flexcart.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.IOrderController;
import com.app.flexcart.flexcart.backend.controller.schema.OrderResponse;
import com.app.flexcart.flexcart.backend.controller.util.OrderResponseGenerator;
import com.app.flexcart.flexcart.backend.service.OrderService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OrderControllerImpl implements IOrderController {
    private final OrderService orderService;
    private final OrderResponseGenerator orderResponseGenerator;

    @Override
    public ResponseEntity<String> placeOrder(@NotNull Long userId) {
        orderService.placeOrder(userId);
        return ResponseEntity.ok("Order is placed successfully");
    }

    @Override
    public ResponseEntity<List<OrderResponse>> getOrders(@NotNull Long userId) {
        var orders = orderService.getOrders(userId);
        var response = orderResponseGenerator.generateOrderResponseList(orders);
        return ResponseEntity.ok(response);

    }
}

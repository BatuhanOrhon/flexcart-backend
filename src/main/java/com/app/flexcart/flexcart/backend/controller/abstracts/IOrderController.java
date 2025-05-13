package com.app.flexcart.flexcart.backend.controller.abstracts;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.flexcart.flexcart.backend.controller.schema.OrderResponse;

import jakarta.validation.constraints.NotNull;

@RequestMapping("/api")
public interface IOrderController {

    @PostMapping("/order/{userId}")
    public ResponseEntity<String> placeOrder(@NotNull @PathVariable Long userId);

    @GetMapping("/order/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrders(@NotNull @PathVariable Long userId);
}

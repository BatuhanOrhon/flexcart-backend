package com.app.flexcart.flexcart.backend.controller.abstracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.constraints.NotNull;

@RequestMapping("/api")
public interface IOrderController {

    @PostMapping("/order/{userId}")
    public ResponseEntity<String> placeOrder(@NotNull @PathVariable Long userId);
}

package com.app.flexcart.flexcart.backend.controller.abstracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.flexcart.flexcart.backend.controller.schema.GetCartResponse;
import com.app.flexcart.flexcart.backend.controller.schema.PostCartItemRequest;

import jakarta.validation.Valid;

@RequestMapping("/api")
public interface ICartController {
    @PostMapping("/cart/{userId}/item")
    public ResponseEntity<String> addCartItem(@Valid @RequestBody PostCartItemRequest cartRequest,
            @PathVariable Long userId);

    @GetMapping("/cart/{userId}")
    public ResponseEntity<GetCartResponse> getCart(@PathVariable Long userId);
}

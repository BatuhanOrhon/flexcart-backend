package com.app.flexcart.flexcart.backend.controller.abstracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.flexcart.flexcart.backend.controller.schema.GetCartResponse;
import com.app.flexcart.flexcart.backend.controller.schema.PostCartItemRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RequestMapping("/api")
public interface ICartController {
        @PostMapping("/cart/{userId}/item")
        public ResponseEntity<String> addCartItem(@Valid @RequestBody PostCartItemRequest cartRequest,
                        @PathVariable Long userId);

        @GetMapping("/cart/{userId}")
        public ResponseEntity<GetCartResponse> getCart(@NotNull @PathVariable Long userId);

        @DeleteMapping("/cart/{userId}/item/{itemId}")
        public ResponseEntity<?> removeFromCart(
                        @PathVariable Long userId,
                        @PathVariable Long itemId,
                        @NotNull @RequestParam int quantity);

        @DeleteMapping("/cart/{userId}")
        public ResponseEntity<?> emptyCart(
                        @PathVariable Long userId);
}

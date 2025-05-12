package com.app.flexcart.flexcart.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.ICartController;
import com.app.flexcart.flexcart.backend.controller.schema.GetCartResponse;
import com.app.flexcart.flexcart.backend.controller.schema.PostCartItemRequest;
import com.app.flexcart.flexcart.backend.service.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CartControllerImpl implements ICartController {
    private final CartService cartService;

    @Override
    @PostMapping("/cart/{userId}/item")
    public ResponseEntity<String> addCartItem(PostCartItemRequest cartRequest, Long userId) {
        cartService.addCartItem(userId, cartRequest.getProductId(), cartRequest.getQuantity());
        return ResponseEntity.ok("Cart item is added successfully");
    }

    @Override
    public ResponseEntity<GetCartResponse> getCart(Long userId) {
        cartService.getCart(userId);
        return ResponseEntity.ok(new GetCartResponse());
    }

}

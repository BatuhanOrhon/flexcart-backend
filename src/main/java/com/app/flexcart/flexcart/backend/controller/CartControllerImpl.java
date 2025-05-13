package com.app.flexcart.flexcart.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.ICartController;
import com.app.flexcart.flexcart.backend.controller.schema.GetCartResponse;
import com.app.flexcart.flexcart.backend.controller.schema.PostCartItemRequest;
import com.app.flexcart.flexcart.backend.service.CartService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CartControllerImpl implements ICartController {
    private final CartService cartService;

    @Override
    public ResponseEntity<String> addCartItem(PostCartItemRequest cartRequest, Long userId) {
        cartService.addCartItem(userId, cartRequest.getProductId(), cartRequest.getQuantity());
        return ResponseEntity.ok("Cart item is added successfully");
    }

    @Override
    public ResponseEntity<GetCartResponse> getCart(Long userId) {
        return ResponseEntity.ok(cartService.getCartResponse(userId));
    }

    @Override
    public ResponseEntity<?> removeFromCart(Long userId, Long itemId, @NotNull int quantity) {
        cartService.removeFromCart(userId, itemId, quantity);
        return ResponseEntity.ok("Item is removed from cart successfully");
    }

    @Override
    public ResponseEntity<?> emptyCart(Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart is cleared successfully");
    }

}

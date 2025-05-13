package com.app.flexcart.flexcart.backend.controller.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.app.flexcart.flexcart.backend.controller.schema.subclasses.CartItemResponse;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

@Component
public class CartItemResponseGenerator {
    public List<CartItemResponse> generateCartItemResponseList(Cart cart) {
        return cart.getCartItems().stream()
                .map(cartItem -> new CartItemResponse(cartItem.getProduct().getProductId(), cartItem.getQuantity(),
                        cartItem.getProduct().getPrice()))
                .collect(Collectors.toList());
    }
}

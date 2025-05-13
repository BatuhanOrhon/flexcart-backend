package com.app.flexcart.flexcart.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.ICartController;
import com.app.flexcart.flexcart.backend.controller.schema.GetCartResponse;
import com.app.flexcart.flexcart.backend.controller.schema.PostCartItemRequest;
import com.app.flexcart.flexcart.backend.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Tag(name = "Cart Management", description = "APIs for managing the shopping cart")
@RequiredArgsConstructor
@RestController
public class CartControllerImpl implements ICartController {
    private final CartService cartService;

    @Override
    @Operation(summary = "Add an item to the cart", description = "Adds a product to the user's cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item is added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User or product not found")
    })
    public ResponseEntity<String> addCartItem(PostCartItemRequest cartRequest, Long userId) {
        cartService.addCartItem(userId, cartRequest.getProductId(), cartRequest.getQuantity());
        return ResponseEntity.ok("Cart item is added successfully");
    }

    @Override
    @Operation(summary = "Get the cart", description = "Fetches the current state of the user's cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    public ResponseEntity<GetCartResponse> getCart(Long userId) {
        return ResponseEntity.ok(cartService.getCartResponse(userId));
    }

    @Override
    @Operation(summary = "Remove an item from the cart", description = "Removes a specific item from the user's cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item is removed from cart successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Cart or item not found")
    })
    public ResponseEntity<?> removeFromCart(Long userId, Long itemId, @NotNull int quantity) {
        cartService.removeFromCart(userId, itemId, quantity);
        return ResponseEntity.ok("Item is removed from cart successfully");
    }

    @Override
    @Operation(summary = "Empty the cart", description = "Clears all items from the user's cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart is cleared successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    public ResponseEntity<?> emptyCart(Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart is cleared successfully");
    }

}

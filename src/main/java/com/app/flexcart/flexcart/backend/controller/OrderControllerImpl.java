package com.app.flexcart.flexcart.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.IOrderController;
import com.app.flexcart.flexcart.backend.controller.schema.OrderResponse;
import com.app.flexcart.flexcart.backend.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Tag(name = "Order Management", description = "APIs for managing orders")
@RequiredArgsConstructor
@RestController
public class OrderControllerImpl implements IOrderController {
    private final OrderService orderService;

    @Override
    @Operation(summary = "Place an order", description = "Places an order for the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order is placed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "404", description = "User or cart not found")
    })
    public ResponseEntity<String> placeOrder(@NotNull Long userId) {
        orderService.placeOrder(userId);
        return ResponseEntity.ok("Order is placed successfully");
    }

    @Override
    @Operation(summary = "Get user orders", description = "Fetches all orders for the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No orders found for given user ID"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    public ResponseEntity<List<OrderResponse>> getOrders(@NotNull Long userId) {
        var orders = orderService.getOrdersResponse(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);

    }
}

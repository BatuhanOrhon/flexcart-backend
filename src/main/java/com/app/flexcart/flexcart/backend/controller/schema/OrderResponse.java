package com.app.flexcart.flexcart.backend.controller.schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.app.flexcart.flexcart.backend.controller.schema.subclasses.OrderItemResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Response object for order details")
public class OrderResponse {

    @Schema(description = "ID of the order", example = "12345", required = true)
    private Long orderId;

    @Schema(description = "Date and time when the order was placed", example = "2023-06-01T12:00:00", required = true)
    private LocalDateTime orderDate;

    @Schema(description = "Total price of the order", example = "199.99", required = true)
    private BigDecimal totalPrice;

    @Schema(description = "Discount applied to the order", example = "20.00", required = true)
    private BigDecimal discount;

    @Schema(description = "List of items in the order", required = true)
    private List<OrderItemResponse> items;
}

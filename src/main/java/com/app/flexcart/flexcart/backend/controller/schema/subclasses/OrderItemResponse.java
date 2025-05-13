package com.app.flexcart.flexcart.backend.controller.schema.subclasses;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Response object for order item details")
public class OrderItemResponse {

    @Schema(description = "ID of the product", example = "101", required = true)
    private Long productId;

    @Schema(description = "Quantity of the product", example = "3", required = true)
    private Integer quantity;

    @Schema(description = "Price of the product", example = "29.99", required = true)
    private BigDecimal price;
}

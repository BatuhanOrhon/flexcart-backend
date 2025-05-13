package com.app.flexcart.flexcart.backend.controller.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Request object for adding an item to the cart")
public class PostCartItemRequest {

    @NotNull
    @Schema(description = "ID of the product to be added to the cart", example = "101", required = true)
    private Long productId;

    @NotNull
    @Schema(description = "Quantity of the product to be added", example = "2", required = true)
    private Integer quantity;
}

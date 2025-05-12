package com.app.flexcart.flexcart.backend.controller.schema;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCartItemRequest {
    @NotNull
    Long productId;
    @NotNull
    Integer quantity;
}

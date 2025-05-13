package com.app.flexcart.flexcart.backend.controller.schema.subclasses;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemResponse {
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}

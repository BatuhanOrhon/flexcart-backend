package com.app.flexcart.flexcart.backend.domain.transaction;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private Long productId;
    private BigDecimal price;
    private Integer categoryId;
}

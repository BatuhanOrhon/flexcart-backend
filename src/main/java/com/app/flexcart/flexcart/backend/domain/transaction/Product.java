package com.app.flexcart.flexcart.backend.domain.transaction;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private int categoryId;
}

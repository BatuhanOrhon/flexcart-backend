package com.app.flexcart.flexcart.backend.domain.transaction;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Item {
    private String itemId;
    private String itemName;
    private BigDecimal price;
    private int quantity;
    private int categoryId;
}

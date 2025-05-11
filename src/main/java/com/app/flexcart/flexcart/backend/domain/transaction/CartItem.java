package com.app.flexcart.flexcart.backend.domain.transaction;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CartItem {
    private Product product;
    private int quantity;

    public BigDecimal getSubTotalPrice() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}

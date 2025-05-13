package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import java.math.BigDecimal;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

public class MinTotalCondition implements Condition {
    private final BigDecimal minTotal;

    public MinTotalCondition(BigDecimal minTotal) {
        this.minTotal = minTotal;
    }

    public boolean isSatisfiedBy(Cart cart) {
        return cart.getTotal().compareTo(minTotal) >= 0;
    }
}

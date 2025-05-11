package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import java.math.BigDecimal;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.Order;

public class MinTotalCondition implements Condition {
    private final BigDecimal minTotal;

    public MinTotalCondition(BigDecimal minTotal) {
        this.minTotal = minTotal;
    }

    public boolean isSatisfiedBy(Order order) {
        Cart cart = order.getCart();
        return cart.getTotal().compareTo(minTotal) >= 0;
    }
}

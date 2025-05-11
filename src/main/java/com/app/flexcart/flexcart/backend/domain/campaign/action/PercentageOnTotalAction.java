package com.app.flexcart.flexcart.backend.domain.campaign.action;

import java.math.BigDecimal;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.Order;

public class PercentageOnTotalAction implements Action {
    private final BigDecimal percent;

    public PercentageOnTotalAction(BigDecimal pct) {
        this.percent = pct;
    }

    public BigDecimal apply(Order order) {
        Cart cart = order.getCart();
        return cart.getTotal().multiply(percent).divide(BigDecimal.valueOf(100));
    }
}

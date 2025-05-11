package com.app.flexcart.flexcart.backend.domain.campaign.action;

import java.math.BigDecimal;

import com.app.flexcart.flexcart.backend.domain.transaction.Order;

public class FixedAmountAction implements Action {
    private final BigDecimal amount;

    public FixedAmountAction(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public BigDecimal apply(Order order) {
        return amount;
    }
}

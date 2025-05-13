package com.app.flexcart.flexcart.backend.domain.campaign.action;

import java.math.BigDecimal;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

import lombok.Getter;

@Getter
public class FixedAmountAction implements Action {
    private final BigDecimal amount;

    public FixedAmountAction(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Cart cart) {
        cart.setCurrentDiscount(cart.getCurrentDiscount().add(amount));
    }

    @Override
    public BigDecimal calculate(Cart cart) {
        return amount;
    }
}

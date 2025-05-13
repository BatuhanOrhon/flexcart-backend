package com.app.flexcart.flexcart.backend.domain.campaign.action;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PercentageOnTotalAction implements Action {
    private final BigDecimal percent;

    public PercentageOnTotalAction(BigDecimal pct) {
        this.percent = pct;
    }

    public BigDecimal calculate(Cart cart) {
        return cart.getTotal().multiply(percent).divide(BigDecimal.valueOf(10000, 2), 2, RoundingMode.HALF_EVEN);
    }

    @Override
    public void apply(Cart cart) {
        cart.setCurrentDiscount(cart.getCurrentDiscount().add(calculate(cart)));
    }
}

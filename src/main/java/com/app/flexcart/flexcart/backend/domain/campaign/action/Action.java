package com.app.flexcart.flexcart.backend.domain.campaign.action;

import java.math.BigDecimal;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

public interface Action {
    void apply(Cart cart);

    BigDecimal calculate(Cart cart);
}

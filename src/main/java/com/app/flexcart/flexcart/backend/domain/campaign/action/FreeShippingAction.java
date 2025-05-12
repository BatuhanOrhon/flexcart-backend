package com.app.flexcart.flexcart.backend.domain.campaign.action;

import java.math.BigDecimal;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

public class FreeShippingAction implements Action {

    @Override
    public BigDecimal apply(Cart cart) {
        return cart.getShippingFee();
    }

}

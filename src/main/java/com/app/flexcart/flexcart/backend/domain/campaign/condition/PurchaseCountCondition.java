package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

public class PurchaseCountCondition implements Condition {
    private final int nth;

    public PurchaseCountCondition(int nth) {
        this.nth = nth;
    }

    @Override
    public boolean isSatisfiedBy(Cart cart) {
        return cart.getUser().getOrderCount() + 1 == nth;
    }
}

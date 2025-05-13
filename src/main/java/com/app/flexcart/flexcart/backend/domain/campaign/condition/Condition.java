package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

public interface Condition {
    boolean isSatisfiedBy(Cart cart);
}

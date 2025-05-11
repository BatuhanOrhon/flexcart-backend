package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import com.app.flexcart.flexcart.backend.domain.transaction.Order;

public interface Condition {
    boolean isSatisfiedBy(Order order);
}

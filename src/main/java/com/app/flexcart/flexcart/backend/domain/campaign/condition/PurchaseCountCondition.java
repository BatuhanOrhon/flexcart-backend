package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import com.app.flexcart.flexcart.backend.domain.transaction.Order;
import com.app.flexcart.flexcart.backend.service.UserService;

public class PurchaseCountCondition implements Condition {
    private final int nth;
    private final UserService userService;

    public PurchaseCountCondition(int nth, UserService userService) {
        this.nth = nth;
        this.userService = userService;
    }

    @Override
    public boolean isSatisfiedBy(Order order) {
        return userService.getOrderCount(order.getUser().getUserId()) + 1 == nth;
    }
}

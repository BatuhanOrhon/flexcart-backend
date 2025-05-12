package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import com.app.flexcart.flexcart.backend.domain.transaction.Order;
import com.app.flexcart.flexcart.backend.service.OrderService;

public class PurchaseCountCondition implements Condition {
    private final int nth;
    private final OrderService orderService;

    public PurchaseCountCondition(int nth, OrderService orderService) {
        this.nth = nth;
        this.orderService = orderService;
    }

    @Override
    public boolean isSatisfiedBy(Order order) {
        return orderService.getOrderCountByUserId(order.getUser().getUserId()) + 1 == nth;
    }
}

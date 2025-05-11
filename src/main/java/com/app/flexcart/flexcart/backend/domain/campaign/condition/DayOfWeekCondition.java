package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import java.time.DayOfWeek;

import com.app.flexcart.flexcart.backend.domain.transaction.Order;

public class DayOfWeekCondition implements Condition {
    private final DayOfWeek dayOfWeek;

    public DayOfWeekCondition(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public boolean isSatisfiedBy(Order order) {
        return dayOfWeek.equals(order.getOrderDate().getDayOfWeek());
    }
}

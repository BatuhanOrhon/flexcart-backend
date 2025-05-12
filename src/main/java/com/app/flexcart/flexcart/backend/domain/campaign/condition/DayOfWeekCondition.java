package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

public class DayOfWeekCondition implements Condition {
    private final DayOfWeek dayOfWeek;

    public DayOfWeekCondition(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public boolean isSatisfiedBy(Cart cart) {
        return dayOfWeek.equals(LocalDateTime.now().getDayOfWeek());
    }
}

package com.app.flexcart.flexcart.backend.domain.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.condition.DayOfWeekCondition;
import com.app.flexcart.flexcart.backend.domain.transaction.Order;

public class DayOfWeekConditionTest {
    @Test
    public void testIsSatisfiedBy_ConditionMet() {
        DayOfWeek expectedDay = DayOfWeek.MONDAY;
        DayOfWeekCondition condition = new DayOfWeekCondition(expectedDay);

        Order mockOrder = mock(Order.class);
        when(mockOrder.getOrderDate()).thenReturn(LocalDateTime.of(2025, 5, 12, 10, 0)); // Monday

        boolean result = condition.isSatisfiedBy(mockOrder);

        assertTrue(result);
    }

    @Test
    public void testIsSatisfiedBy_ConditionNotMet() {
        DayOfWeek expectedDay = DayOfWeek.MONDAY;
        DayOfWeekCondition condition = new DayOfWeekCondition(expectedDay);

        Order mockOrder = mock(Order.class);
        when(mockOrder.getOrderDate()).thenReturn(LocalDateTime.of(2025, 5, 13, 10, 0)); // Tuesday

        boolean result = condition.isSatisfiedBy(mockOrder);

        assertFalse(result);
    }
}

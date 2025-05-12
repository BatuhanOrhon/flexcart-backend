package com.app.flexcart.flexcart.backend.domain.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.condition.DayOfWeekCondition;

public class DayOfWeekConditionTest {
    @Test
    public void testIsSatisfiedBy_ConditionMet() {
        DayOfWeek today = LocalDateTime.now().getDayOfWeek();
        DayOfWeekCondition condition = new DayOfWeekCondition(today);

        boolean result = condition.isSatisfiedBy(null);

        assertTrue(result, "Condition should be satisfied when the current day matches.");
    }

    @Test
    public void testIsSatisfiedBy_ConditionNotMet() {
        DayOfWeek notToday = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .getDayOfWeek();
        if (notToday == LocalDateTime.now().getDayOfWeek()) {
            notToday = DayOfWeek.TUESDAY; // Fallback to ensure it's not today
        }

        DayOfWeekCondition condition = new DayOfWeekCondition(notToday);

        boolean result = condition.isSatisfiedBy(null);

        assertFalse(result, "Condition should not be satisfied when the current day does not match.");
    }
}

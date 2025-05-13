package com.app.flexcart.flexcart.backend.domain.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.condition.MinTotalCondition;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

public class MinTotalConditionTest {
    @Test
    public void testIsSatisfiedBy_ConditionMet() {
        BigDecimal minTotal = new BigDecimal("100.00");
        MinTotalCondition condition = new MinTotalCondition(minTotal);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getTotal()).thenReturn(new BigDecimal("150.00"));

        boolean result = condition.isSatisfiedBy(mockCart);

        assertTrue(result);
    }

    @Test
    public void testIsSatisfiedBy_ConditionNotMet() {
        BigDecimal minTotal = new BigDecimal("100.00");
        MinTotalCondition condition = new MinTotalCondition(minTotal);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getTotal()).thenReturn(new BigDecimal("50.00"));

        boolean result = condition.isSatisfiedBy(mockCart);

        assertFalse(result);
    }
}

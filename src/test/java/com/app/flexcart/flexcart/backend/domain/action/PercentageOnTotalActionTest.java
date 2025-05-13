package com.app.flexcart.flexcart.backend.domain.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.action.PercentageOnTotalAction;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

public class PercentageOnTotalActionTest {
    @Test
    public void testApply_PercentageAppliedCorrectly() {
        BigDecimal percent = new BigDecimal("10.00");
        PercentageOnTotalAction action = new PercentageOnTotalAction(percent);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getTotal()).thenReturn(new BigDecimal("200.00"));

        BigDecimal result = action.calculate(mockCart);

        assertEquals(new BigDecimal("20.00"), result);
    }

    @Test
    public void testApply_ZeroTotal() {
        BigDecimal percent = new BigDecimal("10.00");
        PercentageOnTotalAction action = new PercentageOnTotalAction(percent);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getTotal()).thenReturn(BigDecimal.ZERO);

        BigDecimal result = action.calculate(mockCart);

        assertEquals(BigDecimal.valueOf(0, 2), result);
    }

    @Test
    public void testApply_ZeroPercent() {
        BigDecimal percent = BigDecimal.ZERO;
        PercentageOnTotalAction action = new PercentageOnTotalAction(percent);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getTotal()).thenReturn(new BigDecimal("200.00"));

        BigDecimal result = action.calculate(mockCart);

        assertEquals(BigDecimal.valueOf(0, 2), result);
    }
}

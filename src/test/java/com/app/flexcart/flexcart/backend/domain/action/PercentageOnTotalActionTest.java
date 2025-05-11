package com.app.flexcart.flexcart.backend.domain.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.action.PercentageOnTotalAction;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.Order;

public class PercentageOnTotalActionTest {
    @Test
    public void testApply_PercentageAppliedCorrectly() {
        BigDecimal percent = new BigDecimal("10.00");
        PercentageOnTotalAction action = new PercentageOnTotalAction(percent);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getTotal()).thenReturn(new BigDecimal("200.00"));

        Order mockOrder = mock(Order.class);
        when(mockOrder.getCart()).thenReturn(mockCart);

        BigDecimal result = action.apply(mockOrder);

        assertEquals(new BigDecimal("20.00"), result);
    }

    @Test
    public void testApply_ZeroTotal() {
        BigDecimal percent = new BigDecimal("10.00");
        PercentageOnTotalAction action = new PercentageOnTotalAction(percent);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getTotal()).thenReturn(BigDecimal.ZERO);

        Order mockOrder = mock(Order.class);
        when(mockOrder.getCart()).thenReturn(mockCart);

        BigDecimal result = action.apply(mockOrder);

        assertEquals(BigDecimal.valueOf(0, 2), result);
    }

    @Test
    public void testApply_ZeroPercent() {
        BigDecimal percent = BigDecimal.ZERO;
        PercentageOnTotalAction action = new PercentageOnTotalAction(percent);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getTotal()).thenReturn(new BigDecimal("200.00"));

        Order mockOrder = mock(Order.class);
        when(mockOrder.getCart()).thenReturn(mockCart);

        BigDecimal result = action.apply(mockOrder);

        assertEquals(BigDecimal.valueOf(0, 2), result);
    }
}

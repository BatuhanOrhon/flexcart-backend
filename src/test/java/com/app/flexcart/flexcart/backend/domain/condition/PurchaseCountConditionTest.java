package com.app.flexcart.flexcart.backend.domain.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.condition.PurchaseCountCondition;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.user.User;
import com.app.flexcart.flexcart.backend.service.OrderService;

public class PurchaseCountConditionTest {
    @Test
    public void testIsSatisfiedBy_ConditionMet() {
        int nth = 5;
        OrderService mockOrderService = mock(OrderService.class);
        PurchaseCountCondition condition = new PurchaseCountCondition(nth, mockOrderService);

        User mockUser = mock(User.class);
        when(mockUser.getUserId()).thenReturn(1L);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getUser()).thenReturn(mockUser);

        when(mockOrderService.getOrderCountByUserId(1L)).thenReturn(4L);

        boolean result = condition.isSatisfiedBy(mockCart);

        assertTrue(result);
    }

    @Test
    public void testIsSatisfiedBy_ConditionNotMet() {
        int nth = 5;
        OrderService mockOrderService = mock(OrderService.class);
        PurchaseCountCondition condition = new PurchaseCountCondition(nth, mockOrderService);

        User mockUser = mock(User.class);
        when(mockUser.getUserId()).thenReturn(1L);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getUser()).thenReturn(mockUser);

        when(mockOrderService.getOrderCountByUserId(1L)).thenReturn(3L);

        boolean result = condition.isSatisfiedBy(mockCart);

        assertFalse(result);
    }
}

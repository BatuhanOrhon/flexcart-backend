package com.app.flexcart.flexcart.backend.domain.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.condition.PurchaseCountCondition;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.user.User;

public class PurchaseCountConditionTest {
    @Test
    public void testIsSatisfiedBy_ConditionMet() {
        int nth = 5;
        PurchaseCountCondition condition = new PurchaseCountCondition(nth);

        User mockUser = mock(User.class);
        when(mockUser.getUserId()).thenReturn(1L);
        when(mockUser.getOrderCount()).thenReturn(4L);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getUser()).thenReturn(mockUser);

        boolean result = condition.isSatisfiedBy(mockCart);

        assertTrue(result);
    }

    @Test
    public void testIsSatisfiedBy_ConditionNotMet() {
        int nth = 5;
        PurchaseCountCondition condition = new PurchaseCountCondition(nth);

        User mockUser = mock(User.class);
        when(mockUser.getUserId()).thenReturn(1L);
        when(mockUser.getOrderCount()).thenReturn(3L);
        Cart mockCart = mock(Cart.class);
        when(mockCart.getUser()).thenReturn(mockUser);

        boolean result = condition.isSatisfiedBy(mockCart);

        assertFalse(result);
    }
}

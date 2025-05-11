package com.app.flexcart.flexcart.backend.domain.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.condition.PurchaseCountCondition;
import com.app.flexcart.flexcart.backend.domain.transaction.Order;
import com.app.flexcart.flexcart.backend.domain.user.User;
import com.app.flexcart.flexcart.backend.service.UserService;

public class PurchaseCountConditionTest {
    @Test
    public void testIsSatisfiedBy_ConditionMet() {
        int nth = 5;
        UserService mockUserService = mock(UserService.class);
        PurchaseCountCondition condition = new PurchaseCountCondition(nth, mockUserService);

        User mockUser = mock(User.class);
        when(mockUser.getUserId()).thenReturn(1L);

        Order mockOrder = mock(Order.class);
        when(mockOrder.getUser()).thenReturn(mockUser);

        when(mockUserService.getOrderCount(1L)).thenReturn(4);

        boolean result = condition.isSatisfiedBy(mockOrder);

        assertTrue(result);
    }

    @Test
    public void testIsSatisfiedBy_ConditionNotMet() {
        int nth = 5;
        UserService mockUserService = mock(UserService.class);
        PurchaseCountCondition condition = new PurchaseCountCondition(nth, mockUserService);

        User mockUser = mock(User.class);
        when(mockUser.getUserId()).thenReturn(1L);

        Order mockOrder = mock(Order.class);
        when(mockOrder.getUser()).thenReturn(mockUser);

        when(mockUserService.getOrderCount(1L)).thenReturn(3);

        boolean result = condition.isSatisfiedBy(mockOrder);

        assertFalse(result);
    }
}

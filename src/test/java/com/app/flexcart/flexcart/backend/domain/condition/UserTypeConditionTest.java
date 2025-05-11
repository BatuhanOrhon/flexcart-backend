package com.app.flexcart.flexcart.backend.domain.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.condition.UserTypeCondition;
import com.app.flexcart.flexcart.backend.domain.transaction.Order;
import com.app.flexcart.flexcart.backend.domain.user.User;
import com.app.flexcart.flexcart.backend.domain.user.UserType;

public class UserTypeConditionTest {

    @Test
    public void testIsSatisfiedBy_UserTypeMatches() {
        // Arrange
        UserType expectedUserType = UserType.PREMIUM;
        UserTypeCondition condition = new UserTypeCondition(expectedUserType);

        User mockUser = mock(User.class);
        when(mockUser.getUserType()).thenReturn(UserType.PREMIUM);

        Order mockOrder = mock(Order.class);
        when(mockOrder.getUser()).thenReturn(mockUser);

        // Act
        boolean result = condition.isSatisfiedBy(mockOrder);

        // Assert
        assertTrue(result, "Condition should be satisfied when user type matches.");
    }

    @Test
    public void testIsSatisfiedBy_UserTypeDoesNotMatch() {
        // Arrange
        UserType expectedUserType = UserType.PREMIUM;
        UserTypeCondition condition = new UserTypeCondition(expectedUserType);

        User mockUser = mock(User.class);
        when(mockUser.getUserType()).thenReturn(UserType.STANDARD);

        Order mockOrder = mock(Order.class);
        when(mockOrder.getUser()).thenReturn(mockUser);

        // Act
        boolean result = condition.isSatisfiedBy(mockOrder);

        // Assert
        assertFalse(result, "Condition should not be satisfied when user type does not match.");
    }
}

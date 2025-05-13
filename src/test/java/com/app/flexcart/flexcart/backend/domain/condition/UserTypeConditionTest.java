package com.app.flexcart.flexcart.backend.domain.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.condition.UserTypeCondition;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.user.User;
import com.app.flexcart.flexcart.backend.domain.user.UserType;

public class UserTypeConditionTest {

    @Test
    public void testIsSatisfiedBy_UserTypeMatches() {
        UserType expectedUserType = UserType.PREMIUM;
        UserTypeCondition condition = new UserTypeCondition(expectedUserType);

        User mockUser = mock(User.class);
        when(mockUser.getUserType()).thenReturn(UserType.PREMIUM);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getUser()).thenReturn(mockUser);

        boolean result = condition.isSatisfiedBy(mockCart);

        assertTrue(result, "Condition should be satisfied when user type matches.");
    }

    @Test
    public void testIsSatisfiedBy_UserTypeDoesNotMatch() {
        UserType expectedUserType = UserType.PREMIUM;
        UserTypeCondition condition = new UserTypeCondition(expectedUserType);

        User mockUser = mock(User.class);
        when(mockUser.getUserType()).thenReturn(UserType.STANDARD);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getUser()).thenReturn(mockUser);

        boolean result = condition.isSatisfiedBy(mockCart);

        assertFalse(result, "Condition should not be satisfied when user type does not match.");
    }
}

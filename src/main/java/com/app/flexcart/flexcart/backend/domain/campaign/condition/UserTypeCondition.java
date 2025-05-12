package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.user.User;
import com.app.flexcart.flexcart.backend.domain.user.UserType;

public class UserTypeCondition implements Condition {
    UserType userType;

    public UserTypeCondition(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean isSatisfiedBy(Cart cart) {
        User user = cart.getUser();
        return user.getUserType().equals(userType);
    }
}

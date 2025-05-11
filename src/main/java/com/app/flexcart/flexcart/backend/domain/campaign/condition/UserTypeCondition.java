package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import com.app.flexcart.flexcart.backend.domain.transaction.Order;
import com.app.flexcart.flexcart.backend.domain.user.User;
import com.app.flexcart.flexcart.backend.domain.user.UserType;

public class UserTypeCondition implements Condition {
    UserType userType;

    public UserTypeCondition(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean isSatisfiedBy(Order order) {
        User user = order.getUser();
        return user.getUserType().equals(userType);
    }
}

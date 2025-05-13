package com.app.flexcart.flexcart.backend.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long userId;
    UserType userType;
    Long orderCount;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }
}

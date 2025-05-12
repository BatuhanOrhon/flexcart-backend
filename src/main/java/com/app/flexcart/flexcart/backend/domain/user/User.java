package com.app.flexcart.flexcart.backend.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long userId;
    UserType userType;
}

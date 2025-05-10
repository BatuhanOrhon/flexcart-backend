package com.app.flexcart.flexcart.backend.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String name;
    private String surname;
    private String email;
    private String address;
    UserType userType;
}

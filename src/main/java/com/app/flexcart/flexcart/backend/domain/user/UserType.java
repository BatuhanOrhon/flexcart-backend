package com.app.flexcart.flexcart.backend.domain.user;

import lombok.Getter;

@Getter
public enum UserType {
    PREMIUM("Premium Customer", 1), NEW("New Customer", 2), STANDARD("Standard", 0);

    private String name;
    private int id;

    UserType(String name, int id) {
        this.name = name;
        this.id = id;
    }
}

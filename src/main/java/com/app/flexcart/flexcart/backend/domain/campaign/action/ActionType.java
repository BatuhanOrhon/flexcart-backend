package com.app.flexcart.flexcart.backend.domain.campaign.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionType {
    FIXED_AMOUNT, PERCENTAGE_ON_TOTAL, PERCENTAGE_ON_CATEGORY, PERCENTAGE_ON_PRODUCT, FREE_UNITS_ON_CATEGORY,
    FREE_SHIPPING, FIXED_AMOUNT_ON_CATEGORY;

    @JsonCreator
    public static ActionType from(String value) {
        return ActionType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}

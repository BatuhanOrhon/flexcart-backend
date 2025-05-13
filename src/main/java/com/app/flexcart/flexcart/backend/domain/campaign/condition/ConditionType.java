package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ConditionType {
    DAY_OF_WEEK, MIN_TOTAL, USER_TYPE, PURCHASE_COUNT, CATEGORY_QUANTITY, PRODUCT_QUANTITY;

    @JsonCreator
    public static ConditionType from(String value) {
        return ConditionType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}

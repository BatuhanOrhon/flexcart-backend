package com.app.flexcart.flexcart.backend.service.factory;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.app.flexcart.flexcart.backend.domain.campaign.condition.CategoryQuantityCondition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.Condition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.ConditionType;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.DayOfWeekCondition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.MinTotalCondition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.ProductQuantityCondition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.PurchaseCountCondition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.UserTypeCondition;
import com.app.flexcart.flexcart.backend.domain.user.UserType;
import com.app.flexcart.flexcart.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConditionFactory {
    private final UserService userService;

    public Condition createCondition(String conditionType, Map<String, Object> params) {
        ConditionType type;
        try {
            type = ConditionType.valueOf(conditionType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown action type: " + conditionType, e);
        }
        switch (type) {
            case CATEGORY_QUANTITY:
                var category = (Integer) params.get("category");
                var quantity = (Integer) params.get("quantity");
                return new CategoryQuantityCondition(category, quantity);

            case DAY_OF_WEEK:
                var dayOfWeek = (String) params.get("dayOfWeek");
                DayOfWeek day = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
                return new DayOfWeekCondition(day);

            case MIN_TOTAL:
                var minTotal = (BigDecimal) params.get("minTotal");
                return new MinTotalCondition(minTotal);

            case PRODUCT_QUANTITY:
                var productId = (Long) params.get("productId");
                var productQuantity = (Integer) params.get("productQuantity");
                return new ProductQuantityCondition(productId, productQuantity);

            case PURCHASE_COUNT:
                var purchaseCount = (Integer) params.get("nth");
                return new PurchaseCountCondition(purchaseCount, userService);
            case USER_TYPE:
                var userType = (String) params.get("userType");
                var userTypeEnum = UserType.valueOf(userType.toUpperCase());
                return new UserTypeCondition(userTypeEnum);
            default:
                throw new IllegalArgumentException("Unknown condition type: " + conditionType);
        }
    }
}

package com.app.flexcart.flexcart.backend.service.factory;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ConditionRequest;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.CategoryQuantityCondition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.Condition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.ConditionType;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.DayOfWeekCondition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.MinTotalCondition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.ProductQuantityCondition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.PurchaseCountCondition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.UserTypeCondition;
import com.app.flexcart.flexcart.backend.domain.user.UserType;
import com.app.flexcart.flexcart.backend.exception.ConditionFactoryException;
import com.app.flexcart.flexcart.backend.exception.ConditionFactoryParameterCannotBeNullException;
import com.app.flexcart.flexcart.backend.service.OrderService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConditionFactory {
    private final OrderService orderService;

    public Condition createCondition(ConditionType conditionType, Map<String, Object> params) {
        switch (conditionType) {
            case CATEGORY_QUANTITY:
                var category = params.get("categoryId");
                var quantity = params.get("quantity");

                if (category == null) {
                    throw new ConditionFactoryParameterCannotBeNullException("Category ID cannot be null for CATEGORY_QUANTITY condition");
                }
                if (quantity == null) {
                    throw new ConditionFactoryParameterCannotBeNullException("Quantity cannot be null for CATEGORY_QUANTITY condition");
                }

                return new CategoryQuantityCondition((Integer) category, (Integer) quantity);

            case DAY_OF_WEEK:
                var dayOfWeek = params.get("dayOfWeek");

                if (dayOfWeek == null) {
                    throw new ConditionFactoryParameterCannotBeNullException("Day of week cannot be null for DAY_OF_WEEK condition");
                }

                DayOfWeek day = DayOfWeek.valueOf(((String) dayOfWeek).toUpperCase());
                return new DayOfWeekCondition(day);

            case MIN_TOTAL:
                var minTotal = params.get("minTotal");

                if (minTotal == null) {
                    throw new ConditionFactoryParameterCannotBeNullException("Min total cannot be null for MIN_TOTAL condition");
                }

                return new MinTotalCondition(BigDecimal.valueOf((Integer) minTotal));

            case PRODUCT_QUANTITY:
                var productId = params.get("productId");
                var productQuantity = params.get("productQuantity");

                if (productId == null) {
                    throw new ConditionFactoryParameterCannotBeNullException("Product ID cannot be null for PRODUCT_QUANTITY condition");
                }
                if (productQuantity == null) {
                    throw new ConditionFactoryParameterCannotBeNullException("Product quantity cannot be null for PRODUCT_QUANTITY condition");
                }

                return new ProductQuantityCondition((Long) productId, (Integer) productQuantity);

            case PURCHASE_COUNT:
                var purchaseCount = params.get("nth");

                if (purchaseCount == null) {
                    throw new ConditionFactoryParameterCannotBeNullException("Purchase count cannot be null for PURCHASE_COUNT condition");
                }

                return new PurchaseCountCondition((Integer) purchaseCount, orderService);

            case USER_TYPE:
                var userType = params.get("userType");

                if (userType == null) {
                    throw new ConditionFactoryParameterCannotBeNullException("User type cannot be null for USER_TYPE condition");
                }

                UserType userTypeEnum = UserType.valueOf(((String) userType).toUpperCase());
                return new UserTypeCondition(userTypeEnum);
            default:
                throw new ConditionFactoryException("Unknown condition type: " + conditionType);
        }
    }

        public List<Condition> getConditionList(List<ConditionRequest> requests) {
        return requests.stream()
            .map(r -> createCondition(r.getType(), r.getParameters()))
            .toList();
    }
}
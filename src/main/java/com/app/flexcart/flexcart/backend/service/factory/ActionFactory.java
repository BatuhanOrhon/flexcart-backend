package com.app.flexcart.flexcart.backend.service.factory;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.app.flexcart.flexcart.backend.domain.campaign.action.Action;
import com.app.flexcart.flexcart.backend.domain.campaign.action.ActionType;
import com.app.flexcart.flexcart.backend.domain.campaign.action.FixedAmountAction;
import com.app.flexcart.flexcart.backend.domain.campaign.action.FreeShippingAction;
import com.app.flexcart.flexcart.backend.domain.campaign.action.FreeUnitsOnCategoryAction;
import com.app.flexcart.flexcart.backend.domain.campaign.action.PercentageOnCategoryAction;
import com.app.flexcart.flexcart.backend.domain.campaign.action.PercentageOnTotalAction;

@Component
public class ActionFactory {

    public Action createAction(ActionType actionType, Map<String, Object> params) {
        switch (actionType) {
            case FIXED_AMOUNT:
                var amount = params.get("amount");
                if (amount == null) {
                    throw new IllegalArgumentException("Amount cannot be null for FIXED_AMOUNT action");
                }
                return new FixedAmountAction(BigDecimal.valueOf((Double) amount));

            case FREE_UNITS_ON_CATEGORY:
                var categoryId = params.get("category");
                var freeUnits = params.get("freeUnits");

                if (categoryId == null) {
                    throw new IllegalArgumentException("Category ID cannot be null for FREE_UNITS_ON_CATEGORY action");
                }
                if (freeUnits == null) {
                    throw new IllegalArgumentException("Free units cannot be null for FREE_UNITS_ON_CATEGORY action");
                }

                return new FreeUnitsOnCategoryAction((Integer) categoryId, (Integer) freeUnits);

            case PERCENTAGE_ON_CATEGORY:
                var categoryIdPct = params.get("category");
                var percent = params.get("percent");

                if (categoryIdPct == null) {
                    throw new IllegalArgumentException("Category ID cannot be null for PERCENTAGE_ON_CATEGORY action");
                }
                if (percent == null) {
                    throw new IllegalArgumentException("Percentage cannot be null for PERCENTAGE_ON_CATEGORY action");
                }

                return new PercentageOnCategoryAction((Integer) categoryIdPct, BigDecimal.valueOf((Double) percent));

            case PERCENTAGE_ON_PRODUCT:
                var productId = params.get("product");
                var percentProduct = params.get("percent");

                if (productId == null) {
                    throw new IllegalArgumentException("Product ID cannot be null for PERCENTAGE_ON_PRODUCT action");
                }
                if (percentProduct == null) {
                    throw new IllegalArgumentException("Percentage cannot be null for PERCENTAGE_ON_PRODUCT action");
                }

                return new PercentageOnCategoryAction((Integer) productId, BigDecimal.valueOf((Double) percentProduct));

            case PERCENTAGE_ON_TOTAL:
                var percentTotal = params.get("percent");

                if (percentTotal == null) {
                    throw new IllegalArgumentException("Percentage cannot be null for PERCENTAGE_ON_TOTAL action");
                }

                return new PercentageOnTotalAction(BigDecimal.valueOf((Double) percentTotal));
            case FREE_SHIPPING:
                return new FreeShippingAction();
            default:
                throw new IllegalArgumentException("Unknown action type: " + actionType);
        }
    }
}

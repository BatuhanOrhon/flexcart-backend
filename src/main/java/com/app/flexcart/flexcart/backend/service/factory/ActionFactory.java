package com.app.flexcart.flexcart.backend.service.factory;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.app.flexcart.flexcart.backend.domain.campaign.action.Action;
import com.app.flexcart.flexcart.backend.domain.campaign.action.ActionType;
import com.app.flexcart.flexcart.backend.domain.campaign.action.FixedAmountAction;
import com.app.flexcart.flexcart.backend.domain.campaign.action.FreeUnitsOnCategoryAction;
import com.app.flexcart.flexcart.backend.domain.campaign.action.PercentageOnCategoryAction;
import com.app.flexcart.flexcart.backend.domain.campaign.action.PercentageOnTotalAction;

@Component
public class ActionFactory {

    public Action createAction(String actionType, Map<String, Object> params) {
        ActionType type;
        try {
            type = ActionType.valueOf(actionType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown action type: " + actionType, e);
        }

        switch (type) {
            case FIXED_AMOUNT:
                var amount = (BigDecimal) params.get("amount");
                return new FixedAmountAction(amount);
            case FREE_UNITS_ON_CATEGORY:
                var categoryId = (Integer) params.get("category");
                var freeUnits = (Integer) params.get("freeUnits");
                return new FreeUnitsOnCategoryAction(categoryId, freeUnits);
            case PERCENTAGE_ON_CATEGORY:
                var categoryIdPct = (Integer) params.get("category");
                var percent = (BigDecimal) params.get("percent");
                return new PercentageOnCategoryAction(categoryIdPct, percent);
            case PERCENTAGE_ON_PRODUCT:
                var productId = (Integer) params.get("product");
                var percentProduct = (BigDecimal) params.get("percent");
                return new PercentageOnCategoryAction(productId, percentProduct);
            case PERCENTAGE_ON_TOTAL:
                var percentTotal = (BigDecimal) params.get("percent");
                return new PercentageOnTotalAction(percentTotal);
            default:
                throw new IllegalArgumentException("Unknown action type: " + actionType);
        }
    }
}

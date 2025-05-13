package com.app.flexcart.flexcart.backend.service.factory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ActionRequest;
import com.app.flexcart.flexcart.backend.domain.campaign.action.Action;
import com.app.flexcart.flexcart.backend.domain.campaign.action.ActionType;
import com.app.flexcart.flexcart.backend.domain.campaign.action.FixedAmountAction;
import com.app.flexcart.flexcart.backend.domain.campaign.action.FreeShippingAction;
import com.app.flexcart.flexcart.backend.domain.campaign.action.FreeUnitsOnCategoryAction;
import com.app.flexcart.flexcart.backend.domain.campaign.action.PercentageOnCategoryAction;
import com.app.flexcart.flexcart.backend.domain.campaign.action.PercentageOnTotalAction;
import com.app.flexcart.flexcart.backend.exception.ActionFactoryException;
import com.app.flexcart.flexcart.backend.exception.ActionFactoryParameterCannotBeNullException;

@Component
// TODO @RequiredArgsConstructor neden yok?
public class ActionFactory {

    public Action createAction(ActionType actionType, Map<String, Object> params) {
        if (actionType == null) {
            throw new ActionFactoryException("Unknown action type: " + actionType);
        }
        switch (actionType) {
            case FIXED_AMOUNT:
                var amount = params.get("amount");
                if (amount == null) {
                    throw new ActionFactoryParameterCannotBeNullException("Amount cannot be null for FIXED_AMOUNT action");
                }
                return new FixedAmountAction(BigDecimal.valueOf((Double) amount));

            case FREE_UNITS_ON_CATEGORY:
                var categoryId = params.get("categoryId");
                var freeUnits = params.get("freeUnits");

                if (categoryId == null) {
                    throw new ActionFactoryParameterCannotBeNullException("Category ID cannot be null for FREE_UNITS_ON_CATEGORY action");
                }
                if (freeUnits == null) {
                    throw new ActionFactoryParameterCannotBeNullException("Free units cannot be null for FREE_UNITS_ON_CATEGORY action");
                }

                return new FreeUnitsOnCategoryAction((Integer) categoryId, (Integer) freeUnits);

            case PERCENTAGE_ON_CATEGORY:
                var categoryIdPct = params.get("categoryId");
                var percent = params.get("percent");

                if (categoryIdPct == null) {
                    throw new ActionFactoryParameterCannotBeNullException("Category ID cannot be null for PERCENTAGE_ON_CATEGORY action");
                }
                if (percent == null) {
                    throw new ActionFactoryParameterCannotBeNullException("Percentage cannot be null for PERCENTAGE_ON_CATEGORY action");
                }

                return new PercentageOnCategoryAction((Integer) categoryIdPct, BigDecimal.valueOf((Double) percent));

            case PERCENTAGE_ON_PRODUCT:
                var productId = params.get("product");
                var percentProduct = params.get("percent");

                if (productId == null) {
                    throw new ActionFactoryParameterCannotBeNullException("Product ID cannot be null for PERCENTAGE_ON_PRODUCT action");
                }
                if (percentProduct == null) {
                    throw new ActionFactoryParameterCannotBeNullException("Percentage cannot be null for PERCENTAGE_ON_PRODUCT action");
                }

                return new PercentageOnCategoryAction((Integer) productId, BigDecimal.valueOf((Double) percentProduct));

            case PERCENTAGE_ON_TOTAL:
                var percentTotal = params.get("percent");

                if (percentTotal == null) {
                    throw new ActionFactoryParameterCannotBeNullException("Percentage cannot be null for PERCENTAGE_ON_TOTAL action");
                }

                return new PercentageOnTotalAction(BigDecimal.valueOf((Double) percentTotal));
            case FREE_SHIPPING:
                return new FreeShippingAction();
            default:
                throw new ActionFactoryException("Unknown action type: " + actionType);
        }
    }

    public List<Action> getActionList(List<ActionRequest> actions) {
        return actions.stream()
            .map(ar -> createAction(ar.getType(), ar.getParameters()))
            .toList();
    }
}

package com.app.flexcart.flexcart.backend.service.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

class ActionFactoryTest {
    private ActionFactory factory;

    @BeforeEach
    void init() {
        factory = new ActionFactory();
    }

    @Test
    void createFixedAmountAction_withValidParams_returnsFixedAmountAction() {
        Map<String, Object> params = Map.of("amount", 30.0);
        Action result = factory.createAction(ActionType.FIXED_AMOUNT, params);
        assertTrue(result instanceof FixedAmountAction);
        FixedAmountAction action = (FixedAmountAction) result;
        assertEquals(BigDecimal.valueOf(30.0), action.getAmount());
    }

    @Test
    void createFixedAmountAction_withNullParam_throwsParameterCannotBeNull() {
        Map<String, Object> params = Map.of();
        assertThrows(ActionFactoryParameterCannotBeNullException.class,
                () -> factory.createAction(ActionType.FIXED_AMOUNT, params));
    }

    @Test
    void createFreeUnitsOnCategoryAction_withValidParams_returnsExpected() {
        Map<String, Object> params = Map.of(
                "categoryId", 5,
                "freeUnits", 2
        );
        Action result = factory.createAction(ActionType.FREE_UNITS_ON_CATEGORY, params);
        assertTrue(result instanceof FreeUnitsOnCategoryAction);
        FreeUnitsOnCategoryAction action = (FreeUnitsOnCategoryAction) result;
        assertEquals(5, action.getCategoryId());
        assertEquals(2, action.getFreeUnits());
    }

    @Test
    void createFreeUnitsOnCategoryAction_missingFreeUnits_throwsException() {
        Map<String, Object> params = Map.of("categoryId", 5);
        assertThrows(ActionFactoryParameterCannotBeNullException.class,
                () -> factory.createAction(ActionType.FREE_UNITS_ON_CATEGORY, params));
    }

    @Test
    void createPercentageOnCategoryAction_withValidParams_returnsExpected() {
        Map<String, Object> params = Map.of(
                "categoryId", 3,
                "percent", 15.0
        );
        Action result = factory.createAction(ActionType.PERCENTAGE_ON_CATEGORY, params);
        assertTrue(result instanceof PercentageOnCategoryAction);
        PercentageOnCategoryAction action = (PercentageOnCategoryAction) result;
        assertEquals(3, action.getCategoryId());
        assertEquals(BigDecimal.valueOf(15.0), action.getPercent());
    }

    @Test
    void createPercentageOnTotalAction_withValidParams_returnsExpected() {
        Map<String, Object> params = Map.of("percent", 10.0);
        Action result = factory.createAction(ActionType.PERCENTAGE_ON_TOTAL, params);
        assertTrue(result instanceof PercentageOnTotalAction);
        PercentageOnTotalAction action = (PercentageOnTotalAction) result;
        assertEquals(BigDecimal.valueOf(10.0), action.getPercent());
    }

    @Test
    void createPercentageOnTotalAction_withNullParam_throwsException() {
        Map<String, Object> params = Map.of();
        assertThrows(ActionFactoryParameterCannotBeNullException.class,
                () -> factory.createAction(ActionType.PERCENTAGE_ON_TOTAL, params));
    }

    @Test
    void createFreeShippingAction_returnsFreeShippingAction() {
        Map<String, Object> params = Map.of();
        Action result = factory.createAction(ActionType.FREE_SHIPPING, params);
        assertTrue(result instanceof FreeShippingAction);
    }

    @Test
    void createUnknownActionType_throwsFactoryException() {
        Map<String, Object> params = Map.of();
        assertThrows(ActionFactoryException.class,
                () -> factory.createAction(null, params));
    }

    @Test
    void getActionList_withValidRequests_returnsCorrectActions() {
        ActionRequest r1 = new ActionRequest(ActionType.FIXED_AMOUNT, Map.of("amount", 5.0));
        ActionRequest r2 = new ActionRequest(ActionType.FREE_SHIPPING, Map.of());
        List<Action> actions = factory.getActionList(List.of(r1, r2));

        assertEquals(2, actions.size());
        assertTrue(actions.get(0) instanceof FixedAmountAction);
        assertTrue(actions.get(1) instanceof FreeShippingAction);
    }
}

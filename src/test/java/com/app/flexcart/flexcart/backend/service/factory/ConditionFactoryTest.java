package com.app.flexcart.flexcart.backend.service.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

class ConditionFactoryTest {
    private ConditionFactory factory;

    @BeforeEach
    void setUp() {
        factory = new ConditionFactory();
    }

    @Test
    void createCategoryQuantityCondition_returnsCategoryQuantityCondition() {
        Condition cond = factory.createCondition(
                ConditionType.CATEGORY_QUANTITY,
                Map.of("categoryId", 1, "quantity", 2)
        );
        assertTrue(cond instanceof CategoryQuantityCondition);
    }

    @Test
    void createCategoryQuantityCondition_missingParams_throws() {
        assertThrows(ConditionFactoryParameterCannotBeNullException.class,
                () -> factory.createCondition(ConditionType.CATEGORY_QUANTITY, Map.of()));
    }

    @Test
    void createDayOfWeekCondition_returnsDayOfWeekCondition() {
        Condition cond = factory.createCondition(
                ConditionType.DAY_OF_WEEK,
                Map.of("dayOfWeek", "MONDAY")
        );
        assertTrue(cond instanceof DayOfWeekCondition);
    }

    @Test
    void createDayOfWeekCondition_missingParam_throws() {
        assertThrows(ConditionFactoryParameterCannotBeNullException.class,
                () -> factory.createCondition(ConditionType.DAY_OF_WEEK, Map.of()));
    }

    @Test
    void createMinTotalCondition_returnsMinTotalCondition() {
        Condition cond = factory.createCondition(
                ConditionType.MIN_TOTAL,
                Map.of("minTotal", 100)
        );
        assertTrue(cond instanceof MinTotalCondition);
    }

    @Test
    void createMinTotalCondition_missingParam_throws() {
        assertThrows(ConditionFactoryParameterCannotBeNullException.class,
                () -> factory.createCondition(ConditionType.MIN_TOTAL, Map.of()));
    }

    @Test
    void createProductQuantityCondition_returnsProductQuantityCondition() {
        Condition cond = factory.createCondition(
                ConditionType.PRODUCT_QUANTITY,
                Map.of("productId", 10L, "productQuantity", 3)
        );
        assertTrue(cond instanceof ProductQuantityCondition);
    }

    @Test
    void createProductQuantityCondition_missingParam_throws() {
        assertThrows(ConditionFactoryParameterCannotBeNullException.class,
                () -> factory.createCondition(ConditionType.PRODUCT_QUANTITY, Map.of("productId", 10L)));
    }

    @Test
    void createPurchaseCountCondition_returnsPurchaseCountCondition() {
        Condition cond = factory.createCondition(
                ConditionType.PURCHASE_COUNT,
                Map.of("nth", 5)
        );
        assertTrue(cond instanceof PurchaseCountCondition);
    }

    @Test
    void createPurchaseCountCondition_missingParam_throws() {
        assertThrows(ConditionFactoryParameterCannotBeNullException.class,
                () -> factory.createCondition(ConditionType.PURCHASE_COUNT, Map.of()));
    }

    @Test
    void createUserTypeCondition_returnsUserTypeCondition() {
        Condition cond = factory.createCondition(
                ConditionType.USER_TYPE,
                Map.of("userType", UserType.PREMIUM.name())
        );
        assertTrue(cond instanceof UserTypeCondition);
    }

    @Test
    void createUserTypeCondition_missingParam_throws() {
        assertThrows(ConditionFactoryParameterCannotBeNullException.class,
                () -> factory.createCondition(ConditionType.USER_TYPE, Map.of()));
    }

    @Test
    void createUnknownConditionType_throwsFactoryException() {
        assertThrows(ConditionFactoryException.class,
                () -> factory.createCondition(null, Map.of()));
    }

    @Test
    void getConditionList_mapsRequestsToConditions() {
        ConditionRequest r1 = new ConditionRequest(ConditionType.MIN_TOTAL, Map.of("minTotal", 50));
        ConditionRequest r2 = new ConditionRequest(ConditionType.DAY_OF_WEEK, Map.of("dayOfWeek", "TUESDAY"));

        List<Condition> list = factory.getConditionList(List.of(r1, r2));

        assertEquals(2, list.size());
        assertTrue(list.get(0) instanceof MinTotalCondition);
        assertTrue(list.get(1) instanceof DayOfWeekCondition);
    }
}
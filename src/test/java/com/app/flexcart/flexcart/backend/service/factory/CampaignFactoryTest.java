package com.app.flexcart.flexcart.backend.service.factory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ActionRequest;
import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ConditionRequest;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.domain.campaign.CampaignType;
import com.app.flexcart.flexcart.backend.domain.campaign.action.Action;
import com.app.flexcart.flexcart.backend.domain.campaign.action.ActionType;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.Condition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.ConditionType;
import com.app.flexcart.flexcart.backend.model.entity.CampaignActionEntity;
import com.app.flexcart.flexcart.backend.model.entity.CampaignConditionEntity;
import com.app.flexcart.flexcart.backend.model.entity.CampaignEntity;
import com.app.flexcart.flexcart.backend.util.ParameterParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CampaignFactoryTest {

    @Mock private ActionFactory actionFactory;
    @Mock private ConditionFactory conditionFactory;
    @Mock private ParameterParser parameterParser;

    @InjectMocks private CampaignFactory factory;

    private final String name = "Camp";
    private final String desc = "Desc";
    private final LocalDateTime now = LocalDateTime.now();

    @Test
    void isCampaignCreatable_callsFactoriesAndReturnsTrue() {
        ActionRequest ar = new ActionRequest(ActionType.FIXED_AMOUNT, Map.of("amount", 10.0));
        ConditionRequest cr = new ConditionRequest(ConditionType.MIN_TOTAL, Map.of("minTotal", 50.0));

        when(actionFactory.getActionList(List.of(ar))).thenReturn(List.of(mock(Action.class)));
        when(conditionFactory.getConditionList(List.of(cr))).thenReturn(List.of(mock(Condition.class)));

        boolean result = factory.isCampaignCreatable(name, desc, List.of(ar), List.of(cr));

        assertTrue(result);
        verify(actionFactory).getActionList(List.of(ar));
        verify(conditionFactory).getConditionList(List.of(cr));
    }

    @Test
    void generateDummyCampaignObject_setsAllFieldsCorrectly() {
        var actions = List.<Action>of();
        var conds = List.<Condition>of();
        LocalDateTime start = now;
        LocalDateTime end = now.plusDays(1);

        Campaign camp = factory.generateDummyCampaignObject(5L, name, desc, actions, conds, start, end);

        assertEquals(5L, camp.getId());
        assertEquals(name, camp.getName());
        assertEquals(desc, camp.getDescription());
        assertSame(actions, camp.getActions());
        assertSame(conds, camp.getConditions());
        assertEquals(start, camp.getStartDate());
        assertEquals(end, camp.getEndDate());
        assertEquals(CampaignType.PRICE, camp.getType());
    }

    @Test
    void getCampaignObject_withValidEntities_mapsActionsAndConditions() throws Exception {
        CampaignEntity e = new CampaignEntity();
        e.setCampaignId(7L);
        e.setName(name);
        e.setDescription(desc);
        e.setStartDate(now);
        e.setEndDate(now.plusHours(2));
        e.setType(CampaignType.SHIPPING);

        CampaignActionEntity ae = new CampaignActionEntity();
        ae.setType(ActionType.FIXED_AMOUNT.name());
        ae.setParams("{\"amount\":20.0}");
        ae.setCampaign(e);
        e.setActions(List.of(ae));

        CampaignConditionEntity ce = new CampaignConditionEntity();
        ce.setType(ConditionType.MIN_TOTAL.name());
        ce.setParams("{\"minTotal\":100.0}");
        ce.setCampaign(e);
        e.setConditions(List.of(ce));

        Map<String,Object> parsedParams = Map.of("amount",20.0);
        when(parameterParser.parse(ae.getParams())).thenReturn(parsedParams);
        when(parameterParser.parse(ce.getParams())).thenReturn(Map.of("minTotal",100.0));
        Action actionObj = mock(Action.class);
        Condition condObj = mock(Condition.class);
        when(actionFactory.createAction(ActionType.FIXED_AMOUNT, parsedParams)).thenReturn(actionObj);
        when(conditionFactory.createCondition(ConditionType.MIN_TOTAL, Map.of("minTotal",100.0))).thenReturn(condObj);

        Campaign camp = factory.getCampaignObject(e);

        assertEquals(7L, camp.getId());
        assertEquals(name, camp.getName());
        assertEquals(desc, camp.getDescription());
        assertEquals(CampaignType.SHIPPING, camp.getType());
        assertEquals(1, camp.getActions().size());
        assertSame(actionObj, camp.getActions().get(0));
        assertEquals(1, camp.getConditions().size());
        assertSame(condObj, camp.getConditions().get(0));
    }

    @Test
    void getCampaignObject_skipsInvalidJson() throws Exception {
        CampaignEntity e = new CampaignEntity();
        e.setCampaignId(1L);
        e.setName(name);
        e.setDescription(desc);
        e.setStartDate(now);
        e.setEndDate(now.plusDays(1));
        e.setType(CampaignType.PRICE);

        CampaignActionEntity ae = new CampaignActionEntity();
        ae.setType(ActionType.FIXED_AMOUNT.name());
        ae.setParams("bad json");
        e.setActions(List.of(ae));
        e.setConditions(List.of());

        when(parameterParser.parse(anyString())).thenThrow(new JsonProcessingException("bad"){});

        Campaign camp = factory.getCampaignObject(e);
        assertTrue(camp.getActions().isEmpty());
        assertTrue(camp.getConditions().isEmpty());
    }
}

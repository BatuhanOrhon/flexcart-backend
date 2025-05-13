package com.app.flexcart.flexcart.backend.service.factory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ActionRequest;
import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ConditionRequest;
import com.app.flexcart.flexcart.backend.domain.campaign.CampaignType;
import com.app.flexcart.flexcart.backend.domain.campaign.action.ActionType;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.ConditionType;
import com.app.flexcart.flexcart.backend.exception.InvalidParametersFormatException;
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
class CampaignEntityFactoryTest {

    @Mock
    private ParameterParser parameterParser;

    @InjectMocks
    private CampaignEntityFactory factory;

    private final String name = "Campaign A";
    private final String description = "Desc";
    private final LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
    private final LocalDateTime end = LocalDateTime.of(2025, 1, 10, 23, 59);
    private final CampaignType type = CampaignType.PRICE;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        when(parameterParser.convertToJsonString(anyMap()))
                .thenAnswer(inv -> inv.getArgument(0).toString());
    }

    @Test
    void getCampaignEntity_withValidRequests_createsCorrectEntities() throws JsonProcessingException {
        ActionRequest actionReq = new ActionRequest(ActionType.FIXED_AMOUNT, Map.of("amount", 20.0));
        ConditionRequest condReq = new ConditionRequest(ConditionType.MIN_TOTAL, Map.of("minTotal", 100.0));

        CampaignEntity entity = factory.getCampaignEntity(
                name, description,
                List.of(actionReq), List.of(condReq),
                start, end, type
        );

        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
        assertEquals(start, entity.getStartDate());
        assertEquals(end, entity.getEndDate());
        assertEquals(type, entity.getType());

        List<CampaignActionEntity> actions = entity.getActions();
        assertEquals(1, actions.size());
        CampaignActionEntity ae = actions.get(0);
        assertEquals(ActionType.FIXED_AMOUNT.name(), ae.getType());
        assertEquals(actionReq.getParameters().toString(), ae.getParams());
        assertSame(entity, ae.getCampaign());

        List<CampaignConditionEntity> conditions = entity.getConditions();
        assertEquals(1, conditions.size());
        CampaignConditionEntity ce = conditions.get(0);
        assertEquals(ConditionType.MIN_TOTAL.name(), ce.getType());
        assertEquals(condReq.getParameters().toString(), ce.getParams());
        assertSame(entity, ce.getCampaign());

        verify(parameterParser, times(2)).convertToJsonString(anyMap());
    }

    @Test
    void getCampaignEntity_whenJsonProcessingFails_throwsInvalidParametersFormat() throws JsonProcessingException {
        when(parameterParser.convertToJsonString(anyMap()))
                .thenThrow(new JsonProcessingException("error"){});

        ActionRequest actionReq = new ActionRequest(ActionType.FIXED_AMOUNT, Map.of("amount", 20.0));
        ConditionRequest condReq = new ConditionRequest(ConditionType.MIN_TOTAL, Map.of("minTotal", 100.0));

        assertThrows(InvalidParametersFormatException.class,
                () -> factory.getCampaignEntity(
                        name, description,
                        List.of(actionReq), List.of(condReq),
                        start, end, type
                )
        );
    }
}

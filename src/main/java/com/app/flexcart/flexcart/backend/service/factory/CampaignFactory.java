package com.app.flexcart.flexcart.backend.service.factory;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ActionRequest;
import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ConditionRequest;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.domain.campaign.CampaignType;
import com.app.flexcart.flexcart.backend.domain.campaign.action.Action;
import com.app.flexcart.flexcart.backend.domain.campaign.action.ActionType;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.Condition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.ConditionType;
import com.app.flexcart.flexcart.backend.model.entity.CampaignEntity;
import com.app.flexcart.flexcart.backend.util.ParameterParser;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CampaignFactory {
    private final ActionFactory actionFactory;
    private final ConditionFactory conditionFactory;
    private final ParameterParser parameterParser;

    public Boolean isCampaignCreatable(String name,
            String description,
            List<ActionRequest> actionReqs,
            List<ConditionRequest> conditionReqs) {
        var actions = actionFactory.getActionList(actionReqs);
        var conditions = conditionFactory.getConditionList(conditionReqs);
        generateDummyCampaignObject(0L, name, description, actions, conditions, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));
        return true;
    }

    public Campaign generateDummyCampaignObject(long id, String name, String description, List<Action> actions,
            List<Condition> conditions, LocalDateTime startDate, LocalDateTime endDate) {
        return new Campaign(id, name, description, conditions, actions, startDate, endDate, CampaignType.PRICE);
    }

    public Campaign getCampaignObject(CampaignEntity campaignEntity) {
        var actions = getActions(campaignEntity);
        var conditions = getConditions(campaignEntity);
        return new Campaign(campaignEntity.getCampaignId(), campaignEntity.getName(),
                campaignEntity.getDescription(),
                conditions, actions, campaignEntity.getStartDate(), campaignEntity.getEndDate(),
                campaignEntity.getType());
    }

    private List<Condition> getConditions(CampaignEntity campaignEntity) {
        return campaignEntity.getConditions().stream()
                .map(conditionEntity -> {
                    try {
                        return conditionFactory.createCondition(
                                ConditionType.valueOf(conditionEntity.getType()),
                                parameterParser.parse(conditionEntity.getParams()));
                    } catch (JsonProcessingException e) {
                        log.error(
                                "Error parsing condition parameters of campaign with campaignId = %s. It will be skipped.",
                                e);
                        return null;
                    }
                }).filter(condition -> condition != null)
                .toList();
    }

    private List<Action> getActions(CampaignEntity campaignEntity) {
        return campaignEntity.getActions().stream()
                .map(actionEntity -> {
                    try {
                        return actionFactory.createAction(ActionType.valueOf(actionEntity.getType()),
                                parameterParser.parse(actionEntity.getParams()));
                    } catch (JsonProcessingException e) {
                        log.error(
                                "Error parsing action parameters of campaign with campaignId = %s. It will be skipped.",
                                e);
                        return null;
                    }
                }).filter(action -> action != null)
                .toList();
    }

}

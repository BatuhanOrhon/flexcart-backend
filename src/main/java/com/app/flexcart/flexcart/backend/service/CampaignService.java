package com.app.flexcart.flexcart.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ActionRequest;
import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ConditionRequest;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.domain.campaign.action.Action;
import com.app.flexcart.flexcart.backend.domain.campaign.action.ActionType;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.Condition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.ConditionType;
import com.app.flexcart.flexcart.backend.model.entity.CampaignActionEntity;
import com.app.flexcart.flexcart.backend.model.entity.CampaignConditionEntity;
import com.app.flexcart.flexcart.backend.model.entity.CampaignEntity;
import com.app.flexcart.flexcart.backend.model.repository.CampaignRepository;
import com.app.flexcart.flexcart.backend.service.factory.ActionFactory;
import com.app.flexcart.flexcart.backend.service.factory.ConditionFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final ConditionFactory conditionFactory;
    private final ActionFactory actionFactory;
    private final CampaignRepository campaignRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    public Campaign getCampaignObject(String name, String description, List<Action> actions,
            List<Condition> conditions) {
        return new Campaign(name, description, conditions, actions);
    }

    public List<Campaign> getActiveCampaigns() {
        return campaignRepository.findActiveCampaigns(LocalDateTime.now()).stream()
                .map(campaignEntity -> {

                    var actions = getActions(campaignEntity);

                    var conditions = getConditions(campaignEntity);
                    return getCampaignObject(campaignEntity.getName(), campaignEntity.getDescription(), actions,
                            conditions);
                })
                .toList();
    }

    private List<Condition> getConditions(CampaignEntity campaignEntity) {
        return campaignEntity.getConditions().stream()
                .map(conditionEntity -> conditionFactory.createCondition(
                        ConditionType.valueOf(conditionEntity.getType()),
                        getParams(conditionEntity.getParams())))
                .toList();
    }

    private List<Action> getActions(CampaignEntity campaignEntity) {
        return campaignEntity.getActions().stream()
                .map(actionEntity -> actionFactory.createAction(ActionType.valueOf(actionEntity.getType()),
                        getParams(actionEntity.getParams())))
                .toList();
    }

    private Map<String, Object> getParams(String params) {
        try {
            return objectMapper.readValue(params, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);// TODO handle
        }
    }

    public void saveCampaign(String name, String description, List<ActionRequest> actions,
            List<ConditionRequest> conditions,
            LocalDateTime startDate, LocalDateTime endDate) {

        isValidCampaign(name, description, actions, conditions);
        var campaignEntity = new CampaignEntity();
        campaignEntity.setName(name);
        campaignEntity.setDescription(description);
        campaignEntity.setStartDate(startDate);
        campaignEntity.setEndDate(endDate);

        campaignEntity.setActions(generateActionEntities(actions, campaignEntity));
        campaignEntity.setConditions(generateConditionEntities(conditions, campaignEntity));

        campaignRepository.save(campaignEntity);
    }

    private List<CampaignConditionEntity> generateConditionEntities(List<ConditionRequest> conditions,
            CampaignEntity campaignEntity) {
        return conditions.stream()
                .map(conditionRequest -> {
                    var conditionEntity = new CampaignConditionEntity();
                    conditionEntity.setType(conditionRequest.getType().name());
                    try {
                        conditionEntity.setParams(objectMapper.writeValueAsString(conditionRequest.getParameters()));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);// TODO handle
                    }
                    conditionEntity.setCampaign(campaignEntity);
                    return conditionEntity;
                })
                .toList();
    }

    private List<CampaignActionEntity> generateActionEntities(List<ActionRequest> actions,
            CampaignEntity campaignEntity) {
        return actions.stream()
                .map(actionRequest -> {
                    var actionEntity = new CampaignActionEntity();
                    actionEntity.setType(actionRequest.getType().name());
                    try {
                        actionEntity.setParams(objectMapper.writeValueAsString(actionRequest.getParameters()));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);// TODO handle
                    }
                    actionEntity.setCampaign(campaignEntity);
                    return actionEntity;
                })
                .toList();
    }

    private Campaign isValidCampaign(String name, String description, List<ActionRequest> actions,
            List<ConditionRequest> conditions) {
        var actionList = getActionList(actions);
        var conditionList = getConditionList(conditions);
        return getCampaignObject(name, description, actionList, conditionList);
    }

    private List<Condition> getConditionList(List<ConditionRequest> conditions) {
        return conditions.stream()
                .map(conditionRequest -> conditionFactory.createCondition(conditionRequest.getType(),
                        conditionRequest.getParameters()))
                .toList();
    }

    private List<Action> getActionList(List<ActionRequest> actions) {
        return actions.stream()
                .map(actionRequest -> actionFactory.createAction(actionRequest.getType(),
                        actionRequest.getParameters()))
                .toList();
    }
}

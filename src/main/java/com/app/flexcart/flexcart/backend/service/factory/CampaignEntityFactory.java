package com.app.flexcart.flexcart.backend.service.factory;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ActionRequest;
import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ConditionRequest;
import com.app.flexcart.flexcart.backend.exception.InvalidParametersFormatException;
import com.app.flexcart.flexcart.backend.model.entity.CampaignActionEntity;
import com.app.flexcart.flexcart.backend.model.entity.CampaignConditionEntity;
import com.app.flexcart.flexcart.backend.model.entity.CampaignEntity;
import com.app.flexcart.flexcart.backend.util.ParameterParser;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CampaignEntityFactory {

    private final ParameterParser parameterParser;

    public CampaignEntity getCampaignEntity(String name, String description,
            List<ActionRequest> actions,
            List<ConditionRequest> conditions, LocalDateTime startDate, LocalDateTime endDate) {
        var campaignEntity = new CampaignEntity();
        campaignEntity.setName(name);
        campaignEntity.setDescription(description);
        campaignEntity.setStartDate(startDate);
        campaignEntity.setEndDate(endDate);
        campaignEntity.setActions(generateActionEntities(actions, campaignEntity));
        campaignEntity.setConditions(generateConditionEntities(conditions, campaignEntity));
        return campaignEntity;
    }

    private List<CampaignConditionEntity> generateConditionEntities(List<ConditionRequest> conditions,
            CampaignEntity campaignEntity) {
        return conditions.stream()
                .map(conditionRequest -> {
                    var conditionEntity = new CampaignConditionEntity();
                    conditionEntity.setType(conditionRequest.getType().name());
                    try {
                        conditionEntity
                                .setParams(parameterParser.convertToJsonString(conditionRequest.getParameters()));
                    } catch (JsonProcessingException e) {
                        throw new InvalidParametersFormatException("Invalid JSON format");
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
                        actionEntity.setParams(parameterParser.convertToJsonString(actionRequest.getParameters()));
                    } catch (JsonProcessingException e) {
                        throw new InvalidParametersFormatException("Invalid JSON format");
                    }
                    actionEntity.setCampaign(campaignEntity);
                    return actionEntity;
                })
                .toList();
    }
}

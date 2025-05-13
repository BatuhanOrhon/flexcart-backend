package com.app.flexcart.flexcart.backend.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ActionRequest;
import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ConditionRequest;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.domain.campaign.action.Action;
import com.app.flexcart.flexcart.backend.domain.campaign.action.ActionType;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.Condition;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.ConditionType;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.model.entity.CampaignEntity;
import com.app.flexcart.flexcart.backend.model.repository.CampaignRepository;
import com.app.flexcart.flexcart.backend.service.factory.ActionFactory;
import com.app.flexcart.flexcart.backend.service.factory.CampaignEntityFactory;
import com.app.flexcart.flexcart.backend.service.factory.CampaignFactory;
import com.app.flexcart.flexcart.backend.service.factory.ConditionFactory;
import com.app.flexcart.flexcart.backend.util.ParameterParser;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CampaignService {

    private final ConditionFactory conditionFactory;
    private final ActionFactory actionFactory;
    private final CampaignFactory campaignFactory;
    private final CampaignRepository campaignRepository;
    private final ParameterParser parameterParser;
    CampaignEntityFactory campaignEntityFactory;

    // TODO add free shipping logic
    public Optional<Campaign> findBestCampaign(Cart cart) {
        return getActiveCampaigns().stream().filter(c -> c.isApplicable(cart))
                .max(Comparator.comparing(c -> c.calculateDiscount(cart)));
    }

    public List<Campaign> getActiveCampaigns() {
        return campaignRepository.findActiveCampaigns(LocalDateTime.now()).stream()
                .map(campaignEntity -> {

                    var actions = getActions(campaignEntity);

                    var conditions = getConditions(campaignEntity);
                    return campaignFactory.getCampaignObject(campaignEntity.getCampaignId(), campaignEntity.getName(),
                            campaignEntity.getDescription(),
                            actions,
                            conditions);
                })
                .toList();
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

    public void saveCampaign(String name, String description, List<ActionRequest> actions,
            List<ConditionRequest> conditions,
            LocalDateTime startDate, LocalDateTime endDate) {
        if (campaignFactory.isCampaignCreatable(name, description, actions, conditions)) {
            var campaignEntity = campaignEntityFactory.getCampaignEntity(name, description, actions, conditions,
                    startDate,
                    endDate);
            campaignRepository.save(campaignEntity);
        }
    }

}

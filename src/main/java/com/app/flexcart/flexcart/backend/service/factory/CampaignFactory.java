package com.app.flexcart.flexcart.backend.service.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ActionRequest;
import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ConditionRequest;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.domain.campaign.action.Action;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.Condition;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CampaignFactory {
    private final ActionFactory actionFactory;
    private final ConditionFactory conditionFactory;

    public Campaign createCampaign(String name,
                                   String description,
                                   List<ActionRequest>  actionReqs,
                                   List<ConditionRequest> conditionReqs) {
        var actions    = actionFactory.getActionList(actionReqs);
        var conditions = conditionFactory.getConditionList(conditionReqs);
        return getCampaignObject(name, description, actions, conditions);
    }

    
    public Campaign getCampaignObject(String name, String description, List<Action> actions,
            List<Condition> conditions) {
        return new Campaign(name, description, conditions, actions);
    }

}

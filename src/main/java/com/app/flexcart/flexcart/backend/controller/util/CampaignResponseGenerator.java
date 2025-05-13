package com.app.flexcart.flexcart.backend.controller.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.app.flexcart.flexcart.backend.controller.schema.subclasses.CampaignResponse;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;

@Component
public class CampaignResponseGenerator {
    public List<CampaignResponse> generateCampaignResponseList(List<Campaign> campaign) {
        return campaign.stream()
                .map(c -> new CampaignResponse(c.getName(), c.getDescription()))
                .collect(Collectors.toList());
    }
}

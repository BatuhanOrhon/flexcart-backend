package com.app.flexcart.flexcart.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.ICampaignController;
import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.service.CampaignService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CampaignControllerImpl implements ICampaignController {

    private final CampaignService campaignService;

    @Override
    public ResponseEntity<String> getAllCampaigns() {
        var campaigns = campaignService.getActiveCampaigns();
        return ResponseEntity.ok("Campaigns are fetched successfully");
    }

    @Override
    public ResponseEntity<String> createCampaign(PostCampaignRequest request) {
        campaignService.saveCampaign(request.getName(), request.getDescription(),
                request.getActions(),
                request.getConditions(), request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok("Campaign is created successfully");
    }

    @Override
    public ResponseEntity<Campaign> updateCampaign(Long id, Campaign campaign) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCampaign'");
    }

    @Override
    public ResponseEntity<Void> deleteCampaign(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCampaign'");
    }

}

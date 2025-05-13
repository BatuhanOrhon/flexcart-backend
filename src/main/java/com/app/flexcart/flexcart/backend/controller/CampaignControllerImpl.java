package com.app.flexcart.flexcart.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.ICampaignController;
import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest;
import com.app.flexcart.flexcart.backend.controller.schema.subclasses.CampaignResponse;
import com.app.flexcart.flexcart.backend.controller.util.CampaignResponseGenerator;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.service.CampaignService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CampaignControllerImpl implements ICampaignController {

    private final CampaignService campaignService;

    private final CampaignResponseGenerator campaignResponseGenerator;

    @Override
    public ResponseEntity<List<CampaignResponse>> getAllCampaigns() {
        var campaignList = campaignService.getActiveCampaigns();
        var responseList = campaignResponseGenerator.generateCampaignResponseList(campaignList);
        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<String> createCampaign(PostCampaignRequest request) {
        campaignService.saveCampaign(request.getName(), request.getDescription(),
                request.getActions(),
                request.getConditions(), request.getStartDate(), request.getEndDate());
        return ResponseEntity.created(null).body("Campaign is created successfully");
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

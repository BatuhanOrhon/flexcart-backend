package com.app.flexcart.flexcart.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.ICampaignController;
import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest;
import com.app.flexcart.flexcart.backend.controller.schema.subclasses.CampaignResponse;
import com.app.flexcart.flexcart.backend.controller.util.CampaignResponseGenerator;
import com.app.flexcart.flexcart.backend.service.CampaignService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Campaign Management", description = "APIs for managing campaigns")
@RequiredArgsConstructor
@RestController
public class CampaignControllerImpl implements ICampaignController {

    private final CampaignService campaignService;

    private final CampaignResponseGenerator campaignResponseGenerator;

    @Operation(summary = "Get all active campaigns", description = "Fetches a list of all active campaigns.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of campaigns"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "204", description = "No active campaigns found")
    })
    @Override
    public ResponseEntity<List<CampaignResponse>> getAllCampaigns() {
        var campaignList = campaignService.getActiveCampaigns();
        if (campaignList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        var responseList = campaignResponseGenerator.generateCampaignResponseList(campaignList);
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "Create a new campaign", description = "Creates a new campaign with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Campaign created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Override
    public ResponseEntity<String> createCampaign(PostCampaignRequest request) {
        campaignService.saveCampaign(request.getName(), request.getDescription(),
                request.getActions(),
                request.getConditions(), request.getStartDate(), request.getEndDate(), request.getType());
        return ResponseEntity.created(null).body("Campaign is created successfully");
    }

    @Operation(summary = "Delete a campaign", description = "Deletes a campaign by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Campaign deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Campaign not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Override
    public ResponseEntity<Void> deleteCampaign(Long id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build();
    }

}

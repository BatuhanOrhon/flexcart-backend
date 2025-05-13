package com.app.flexcart.flexcart.backend.controller.abstracts;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest;
import com.app.flexcart.flexcart.backend.controller.schema.subclasses.CampaignResponse;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;

import jakarta.validation.Valid;

@RequestMapping("/api")
public interface ICampaignController {

    @GetMapping("/campaign")
    ResponseEntity<List<CampaignResponse>> getAllCampaigns();

    @PostMapping("/campaign")
    ResponseEntity<String> createCampaign(@Valid @RequestBody PostCampaignRequest campaign);

    @PutMapping("/campaign/{id}")
    ResponseEntity<Campaign> updateCampaign(@PathVariable Long id, @Valid @RequestBody Campaign campaign);

    @DeleteMapping("/campaign/{id}")
    ResponseEntity<Void> deleteCampaign(@PathVariable Long id);
}

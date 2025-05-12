package com.app.flexcart.flexcart.backend.controller.abstracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.flexcart.flexcart.backend.controller.schema.CreateCampaignRequest;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;

@RequestMapping("/api")
public interface ICampaignController {

    @GetMapping("/campaigns")
    ResponseEntity<String> getAllCampaigns();

    @PostMapping("/campaigns")
    ResponseEntity<String> createCampaign(@RequestBody CreateCampaignRequest campaign);

    @PutMapping("/campaigns/{id}")
    ResponseEntity<Campaign> updateCampaign(@PathVariable Long id, @RequestBody Campaign campaign);

    @DeleteMapping("/campaigns/{id}")
    ResponseEntity<Void> deleteCampaign(@PathVariable Long id);
}

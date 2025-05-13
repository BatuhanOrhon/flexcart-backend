package com.app.flexcart.flexcart.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ActionRequest;
import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ConditionRequest;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.domain.campaign.CampaignType;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.exception.CampaignNotFoundException;
import com.app.flexcart.flexcart.backend.model.repository.CampaignRepository;
import com.app.flexcart.flexcart.backend.service.factory.CampaignEntityFactory;
import com.app.flexcart.flexcart.backend.service.factory.CampaignFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CampaignService {

    private final CampaignFactory campaignFactory;
    private final CampaignRepository campaignRepository;
    private final CampaignEntityFactory campaignEntityFactory;

    public List<Campaign> applyBestCampaigns(Cart cart) {

        var activeCampaigns = getActiveCampaigns();

        var bestPriceCampaign = getBestCampaign(cart, activeCampaigns, CampaignType.PRICE);

        var campaignList = new ArrayList<Campaign>();

        if (bestPriceCampaign.isPresent()) {
            bestPriceCampaign.get().applyDiscount(cart);
            campaignList.add(bestPriceCampaign.get());
        }
        var bestShippingCampaign = getBestCampaign(cart, activeCampaigns, CampaignType.SHIPPING);

        if (bestShippingCampaign.isPresent()) {
            bestShippingCampaign.get().applyDiscount(cart);
            campaignList.add(bestShippingCampaign.get());

        }
        return campaignList;
    }

    private Optional<Campaign> getBestCampaign(Cart cart, List<Campaign> activeCampaigns, CampaignType type) {
        return activeCampaigns.stream()
                .filter(c -> (c.getType() == type && c.isApplicable(cart)))
                .max(Comparator.comparing(c -> c.calculateDiscount(cart)))
                .filter(c -> c.calculateDiscount(cart).compareTo(BigDecimal.ZERO) > 0);
    }

    public List<Campaign> getActiveCampaigns() {
        return campaignRepository.findActiveCampaigns(LocalDateTime.now()).stream()
                .map(campaignEntity -> {

                    return campaignFactory.getCampaignObject(campaignEntity);
                })
                .toList();
    }

    public void saveCampaign(String name, String description, List<ActionRequest> actions,
            List<ConditionRequest> conditions,
            LocalDateTime startDate, LocalDateTime endDate, CampaignType type) {
        if (campaignFactory.isCampaignCreatable(name, description, actions, conditions)) {
            var campaignEntity = campaignEntityFactory.getCampaignEntity(name, description, actions, conditions,
                    startDate,
                    endDate, type);
            campaignRepository.save(campaignEntity);
        }
    }

    public void deleteCampaign(Long campaignId) {
        var campaign = campaignRepository.findById(campaignId).orElseThrow(() -> new CampaignNotFoundException(
                "Campaign not found"));
        campaignRepository.delete(campaign);
    }

}

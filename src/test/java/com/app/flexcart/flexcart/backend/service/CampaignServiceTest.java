package com.app.flexcart.flexcart.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ActionRequest;
import com.app.flexcart.flexcart.backend.controller.schema.PostCampaignRequest.ConditionRequest;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.domain.campaign.CampaignType;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.exception.CampaignNotFoundException;
import com.app.flexcart.flexcart.backend.model.entity.CampaignEntity;
import com.app.flexcart.flexcart.backend.model.repository.CampaignRepository;
import com.app.flexcart.flexcart.backend.service.factory.CampaignEntityFactory;
import com.app.flexcart.flexcart.backend.service.factory.CampaignFactory;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {

    @Mock
    private CampaignFactory campaignFactory;
    @Mock
    private CampaignRepository campaignRepository;
    @Mock
    private CampaignEntityFactory campaignEntityFactory;
    @InjectMocks
    private CampaignService campaignService;

    @Mock
    private Cart cart;

    @BeforeEach
    void setUp() {
    }

    @Test
    void applyBestCampaigns_noActiveCampaigns_returnsEmptyList() {
        when(campaignRepository.findActiveCampaigns(any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());
        List<Campaign> result = campaignService.applyBestCampaigns(cart);
        assertTrue(result.isEmpty());
    }

    @Test
    void applyBestCampaigns_priceCampaignApplied_onlyPriceCampaign() {
        Campaign priceCampaign = mock(Campaign.class);
        when(priceCampaign.getType()).thenReturn(CampaignType.PRICE);
        when(priceCampaign.isApplicable(cart)).thenReturn(true);
        when(priceCampaign.calculateDiscount(cart)).thenReturn(BigDecimal.valueOf(50));

        Campaign shippingCampaign = mock(Campaign.class);
        when(shippingCampaign.getType()).thenReturn(CampaignType.SHIPPING);
        when(shippingCampaign.isApplicable(cart)).thenReturn(false);

        List<CampaignEntity> entities = Arrays.asList(new CampaignEntity(), new CampaignEntity());
        when(campaignRepository.findActiveCampaigns(any(LocalDateTime.class)))
                .thenReturn(entities);
        when(campaignFactory.getCampaignObject(any(CampaignEntity.class)))
                .thenReturn(priceCampaign)
                .thenReturn(shippingCampaign);

        List<Campaign> result = campaignService.applyBestCampaigns(cart);
        assertEquals(1, result.size());
        assertSame(priceCampaign, result.get(0));
        verify(priceCampaign).applyDiscount(cart);
    }

    @Test
    void applyBestCampaigns_bothCampaignsApplicable_returnsBoth() {
        Campaign priceCampaign = mock(Campaign.class);
        when(priceCampaign.getType()).thenReturn(CampaignType.PRICE);
        when(priceCampaign.isApplicable(cart)).thenReturn(true);
        when(priceCampaign.calculateDiscount(cart)).thenReturn(BigDecimal.valueOf(50));

        Campaign shippingCampaign = mock(Campaign.class);
        when(shippingCampaign.getType()).thenReturn(CampaignType.SHIPPING);
        when(shippingCampaign.isApplicable(cart)).thenReturn(true);
        when(shippingCampaign.calculateDiscount(cart)).thenReturn(BigDecimal.valueOf(20));

        List<CampaignEntity> entities = Arrays.asList(new CampaignEntity(), new CampaignEntity());
        when(campaignRepository.findActiveCampaigns(any(LocalDateTime.class)))
                .thenReturn(entities);
        when(campaignFactory.getCampaignObject(any(CampaignEntity.class)))
                .thenReturn(priceCampaign)
                .thenReturn(shippingCampaign);

        List<Campaign> result = campaignService.applyBestCampaigns(cart);
        assertEquals(2, result.size());
        assertTrue(result.contains(priceCampaign));
        assertTrue(result.contains(shippingCampaign));
        verify(priceCampaign).applyDiscount(cart);
        verify(shippingCampaign).applyDiscount(cart);
    }

    @Test
    void getActiveCampaigns_transformsEntitiesToDomain() {
        CampaignEntity entity1 = new CampaignEntity();
        CampaignEntity entity2 = new CampaignEntity();
        when(campaignRepository.findActiveCampaigns(any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(entity1, entity2));
        Campaign campaign1 = mock(Campaign.class);
        Campaign campaign2 = mock(Campaign.class);
        when(campaignFactory.getCampaignObject(entity1)).thenReturn(campaign1);
        when(campaignFactory.getCampaignObject(entity2)).thenReturn(campaign2);

        List<Campaign> list = campaignService.getActiveCampaigns();
        assertEquals(2, list.size());
        assertSame(campaign1, list.get(0));
        assertSame(campaign2, list.get(1));
    }

    @Test
    void saveCampaign_creatable_savesEntity() {
        String name = "test";
        String desc = "desc";
        List<ActionRequest> actions = Collections.emptyList();
        List<ConditionRequest> conditions = Collections.emptyList();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1);

        when(campaignFactory.isCampaignCreatable(name, desc, actions, conditions)).thenReturn(true);
        CampaignEntity entity = new CampaignEntity();
        when(campaignEntityFactory.getCampaignEntity(name, desc, actions, conditions, start, end, CampaignType.PRICE))
                .thenReturn(entity);

        campaignService.saveCampaign(name, desc, actions, conditions, start, end, CampaignType.PRICE);
        verify(campaignRepository).save(entity);
    }

    @Test
    void saveCampaign_notCreatable_doesNotSave() {
        when(campaignFactory.isCampaignCreatable(anyString(), anyString(), anyList(), anyList()))
                .thenReturn(false);
        campaignService.saveCampaign("n", "d", Collections.emptyList(), Collections.emptyList(), LocalDateTime.now(),
                LocalDateTime.now(), CampaignType.PRICE);
        verify(campaignRepository, never()).save(any());
    }

    @Test
    void deleteCampaign_existing_deletesEntity() {
        CampaignEntity entity = new CampaignEntity();
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(entity));

        campaignService.deleteCampaign(1L);
        verify(campaignRepository).delete(entity);
    }

    @Test
    void deleteCampaign_notExisting_throws() {
        when(campaignRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CampaignNotFoundException.class, () -> campaignService.deleteCampaign(1L));
    }
}

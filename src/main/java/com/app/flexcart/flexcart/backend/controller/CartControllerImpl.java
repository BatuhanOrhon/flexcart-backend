package com.app.flexcart.flexcart.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.ICartController;
import com.app.flexcart.flexcart.backend.controller.schema.GetCartResponse;
import com.app.flexcart.flexcart.backend.controller.schema.PostCartItemRequest;
import com.app.flexcart.flexcart.backend.controller.schema.subclasses.CampaignResponse;
import com.app.flexcart.flexcart.backend.controller.schema.subclasses.CartItemResponse;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.service.CampaignService;
import com.app.flexcart.flexcart.backend.service.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CartControllerImpl implements ICartController {
    private final CartService cartService;
    private final CampaignService campaignService;

    @Override
    @PostMapping("/cart/{userId}/item")
    public ResponseEntity<String> addCartItem(PostCartItemRequest cartRequest, Long userId) {
        cartService.addCartItem(userId, cartRequest.getProductId(), cartRequest.getQuantity());
        return ResponseEntity.ok("Cart item is added successfully");
    }

    @Override
    public ResponseEntity<GetCartResponse> getCart(Long userId) {
        var cart = cartService.getCart(userId);
        var campaign = campaignService.findBestCampaign(cart).orElseThrow();
        GetCartResponse response = new GetCartResponse();
        populateCartItemList(response, cart);
        var campaignList = new ArrayList<Campaign>();
        campaignList.add(campaign);
        populateCampaignNames(response, campaignList);
        return ResponseEntity.ok(response);
    }

    private void populateCampaignNames(GetCartResponse response, List<Campaign> campaign) {
        response.setCampaigns(campaign.stream()
                .map(c -> new CampaignResponse(c.getName(), c.getDescription()))
                .collect(Collectors.toList()));
    }

    private GetCartResponse populateCartItemList(GetCartResponse response, Cart cart) {
        response.setItems(cart.getCartItems().stream()
                .map(cartItem -> new CartItemResponse(cartItem.getProduct().getProductId(), cartItem.getQuantity(),
                        cartItem.getProduct().getPrice()))
                .collect(Collectors.toList()));
        return response;
    }
}

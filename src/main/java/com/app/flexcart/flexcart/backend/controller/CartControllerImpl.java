package com.app.flexcart.flexcart.backend.controller;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.ICartController;
import com.app.flexcart.flexcart.backend.controller.schema.GetCartResponse;
import com.app.flexcart.flexcart.backend.controller.schema.PostCartItemRequest;
import com.app.flexcart.flexcart.backend.controller.util.CampaignResponseGenerator;
import com.app.flexcart.flexcart.backend.controller.util.CartItemResponseGenerator;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.service.CampaignService;
import com.app.flexcart.flexcart.backend.service.CartService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CartControllerImpl implements ICartController {
    private final CartService cartService;
    private final CampaignService campaignService;
    private final CartItemResponseGenerator cartItemResponseGenerator;
    private final CampaignResponseGenerator campaignResponseGenerator;

    @Override
    public ResponseEntity<String> addCartItem(PostCartItemRequest cartRequest, Long userId) {
        cartService.addCartItem(userId, cartRequest.getProductId(), cartRequest.getQuantity());
        return ResponseEntity.ok("Cart item is added successfully");
    }

    @Override
    public ResponseEntity<GetCartResponse> getCart(Long userId) {
        var cart = cartService.getCart(userId);
        if (cart.getCartItems().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        var campaign = campaignService.findBestCampaign(cart);

        var campaignList = new ArrayList<Campaign>();

        campaign.ifPresent(c -> {
            campaignList.add(campaign.get());
        });

        GetCartResponse response = new GetCartResponse();
        response.setItems(cartItemResponseGenerator.generateCartItemResponseList(cart));

        response.setCampaigns(campaignResponseGenerator.generateCampaignResponseList(campaignList));
        response.setDiscount(campaignList.stream()
                .map((c) -> c.calculateDiscount(cart))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        response.setTotalPrice(cart.getTotal());
        response.setShippingFee(cart.getShippingFee());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> removeFromCart(Long userId, Long itemId, @NotNull int quantity) {
        cartService.removeFromCart(userId, itemId, quantity);
        return ResponseEntity.ok("Item is removed from cart successfully");
    }

    @Override
    public ResponseEntity<?> emptyCart(Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart is cleared successfully");
    }

}

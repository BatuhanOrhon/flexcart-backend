package com.app.flexcart.flexcart.backend.controller.schema;

import java.math.BigDecimal;
import java.util.List;

import com.app.flexcart.flexcart.backend.controller.schema.subclasses.CampaignResponse;
import com.app.flexcart.flexcart.backend.controller.schema.subclasses.CartItemResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCartResponse {

    private List<CartItemResponse> items;
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private List<CampaignResponse> campaigns;
    private BigDecimal shippingFee;

}

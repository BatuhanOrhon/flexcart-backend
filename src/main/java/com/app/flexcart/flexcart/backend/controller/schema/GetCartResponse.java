package com.app.flexcart.flexcart.backend.controller.schema;

import java.math.BigDecimal;
import java.util.List;

import com.app.flexcart.flexcart.backend.controller.schema.subclasses.CampaignResponse;
import com.app.flexcart.flexcart.backend.controller.schema.subclasses.CartItemResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Response object for cart details")
public class GetCartResponse {

    @Schema(description = "List of items in the cart", required = true)
    private List<CartItemResponse> items;

    @Schema(description = "Total price of the cart", example = "149.99", required = true)
    private BigDecimal totalPrice;

    @Schema(description = "Discount applied to the cart", example = "20.00", required = true)
    private BigDecimal totalDiscount;

    @Schema(description = "List of campaigns applied to the cart", required = true)
    private List<CampaignResponse> campaigns;
    @Schema(description = "Shipping fee for the cart", example = "25.00", required = true)
    private BigDecimal shippingFee;

}

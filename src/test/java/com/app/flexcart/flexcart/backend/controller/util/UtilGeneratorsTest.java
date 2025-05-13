package com.app.flexcart.flexcart.backend.controller.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.controller.schema.OrderResponse;
import com.app.flexcart.flexcart.backend.controller.schema.subclasses.CampaignResponse;
import com.app.flexcart.flexcart.backend.controller.schema.subclasses.CartItemResponse;
import com.app.flexcart.flexcart.backend.controller.schema.subclasses.OrderItemResponse;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;
import com.app.flexcart.flexcart.backend.domain.transaction.Product;
import com.app.flexcart.flexcart.backend.model.entity.OrderEntity;
import com.app.flexcart.flexcart.backend.model.entity.OrderItemEntity;
import com.app.flexcart.flexcart.backend.model.entity.ProductEntity;

class UtilGeneratorsTest {
    private CampaignResponseGenerator campaignGen;
    private CartItemResponseGenerator cartItemGen;
    private OrderResponseGenerator orderGen;

    @BeforeEach
    void setUp() {
        campaignGen = new CampaignResponseGenerator();
        cartItemGen = new CartItemResponseGenerator();
        orderGen = new OrderResponseGenerator();
    }

    @Test
    void testGenerateCampaignResponseList() {
        Campaign c = new Campaign(7L, "Camp", "Desc", List.of(), List.of(),
                LocalDateTime.of(2025, 5, 1, 0, 0), LocalDateTime.of(2025, 5, 2, 0, 0), null);
        List<CampaignResponse> list = campaignGen.generateCampaignResponseList(List.of(c));
        assertEquals(1, list.size());
        CampaignResponse r = list.get(0);
        assertEquals(7L, r.getId());
        assertEquals("Camp", r.getName());
        assertEquals("Desc", r.getDescription());
        assertEquals(c.getStartDate(), r.getStartDate());
        assertEquals(c.getEndDate(), r.getEndDate());
    }

    @Test
    void testGenerateCartItemResponseList() {
        Product p = new Product(3L, new BigDecimal("12.34"), 5);
        CartItem ci = new CartItem(p, 4);
        Cart cart = new Cart();
        cart.setCartItems(List.of(ci));
        List<CartItemResponse> list = cartItemGen.generateCartItemResponseList(cart);
        assertEquals(1, list.size());
        CartItemResponse r = list.get(0);
        assertEquals(3L, r.getProductId());
        assertEquals(4, r.getQuantity());
        assertEquals(new BigDecimal("12.34"), r.getPrice());
    }

    @Test
    void testGenerateOrderResponseList() {
        OrderEntity oe = new OrderEntity();
        oe.setId(9L);
        oe.setOrderDate(LocalDateTime.of(2025, 4, 20, 14, 30));
        oe.setTotal(new BigDecimal("100"));
        oe.setDiscount(new BigDecimal("15"));
        ProductEntity pe = new ProductEntity();
        pe.setId(11L);
        pe.setPrice(new BigDecimal("25"));
        OrderItemEntity oie = new OrderItemEntity();
        oie.setProduct(pe);
        oie.setQuantity(2);
        oie.setOrder(oe);
        oe.setOrderItems(List.of(oie));

        List<OrderResponse> list = orderGen.generateOrderResponseList(List.of(oe));
        assertEquals(1, list.size());
        OrderResponse or = list.get(0);
        assertEquals(9L, or.getOrderId());
        assertEquals(oe.getOrderDate(), or.getOrderDate());
        assertEquals(new BigDecimal("100"), or.getTotalPrice());
        assertEquals(new BigDecimal("15"), or.getDiscount());
        assertEquals(1, or.getItems().size());
        OrderItemResponse ir = or.getItems().get(0);
        assertEquals(11L, ir.getProductId());
        assertEquals(2, ir.getQuantity());
        assertEquals(new BigDecimal("25"), ir.getPrice());
    }
}

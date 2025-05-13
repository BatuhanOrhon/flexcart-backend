package com.app.flexcart.flexcart.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.app.flexcart.flexcart.backend.controller.schema.OrderResponse;
import com.app.flexcart.flexcart.backend.controller.util.OrderResponseGenerator;
import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;
import com.app.flexcart.flexcart.backend.domain.transaction.Product;
import com.app.flexcart.flexcart.backend.model.entity.CampaignEntity;
import com.app.flexcart.flexcart.backend.model.entity.OrderEntity;
import com.app.flexcart.flexcart.backend.model.entity.OrderItemEntity;
import com.app.flexcart.flexcart.backend.model.repository.OrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private OrderRepository orderRepository;
    @Mock private CartService cartService;
    @Mock private CampaignService campaignService;
    @Mock private OrderResponseGenerator orderResponseGenerator;

    @InjectMocks private OrderService orderService;

    private final Long userId = 1L;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getOrders_shouldReturnListFromRepository() {
        List<OrderEntity> orders = List.of(new OrderEntity(), new OrderEntity());
        when(orderRepository.findByUser_UserId(userId)).thenReturn(orders);

        List<OrderEntity> result = orderService.getOrders(userId);

        assertSame(orders, result);
        verify(orderRepository).findByUser_UserId(userId);
    }

    @Test
    void getOrdersResponse_shouldUseAllArgsConstructorAndDelegateToGenerator() {
        List<OrderEntity> orders = List.of(new OrderEntity());
        OrderResponse response = new OrderResponse(10L, LocalDateTime.now(), new BigDecimal("100"), new BigDecimal("5"), List.of());
        List<OrderResponse> responses = List.of(response);

        when(orderRepository.findByUser_UserId(userId)).thenReturn(orders);
        when(orderResponseGenerator.generateOrderResponseList(orders)).thenReturn(responses);

        List<OrderResponse> result = orderService.getOrdersResponse(userId);

        assertSame(responses, result);
        verify(orderRepository).findByUser_UserId(userId);
        verify(orderResponseGenerator).generateOrderResponseList(orders);
    }

    @Test
    void placeOrder_withoutCampaigns_savesOrderWithNullAppliedCampaigns() {
        CartItem cartItem = new CartItem(new Product(2L, new BigDecimal("100"), 1), 2);
        Cart cart = new Cart();
        cart.setCartItems(List.of(cartItem));
        cart.setShippingFee(new BigDecimal("10"));
        cart.setCurrentDiscount(new BigDecimal("5"));

        when(cartService.getCart(userId)).thenReturn(cart);
        when(campaignService.applyBestCampaigns(cart)).thenReturn(List.of());

        ArgumentCaptor<OrderEntity> captor = ArgumentCaptor.forClass(OrderEntity.class);
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        orderService.placeOrder(userId);

        verify(orderRepository).save(captor.capture());
        OrderEntity saved = captor.getValue();

        assertNull(saved.getAppliedCampaigns());
        assertEquals(new BigDecimal("205"), saved.getTotal());
        assertEquals(new BigDecimal("5"), saved.getDiscount());
        assertNotNull(saved.getOrderDate());
        assertTrue(ChronoUnit.SECONDS.between(saved.getOrderDate(), LocalDateTime.now()) < 1);

        assertEquals(1, saved.getOrderItems().size());
        OrderItemEntity item = saved.getOrderItems().get(0);
        assertEquals(2, item.getQuantity());
        assertEquals(new BigDecimal("100"), item.getUnitPrice());
        assertEquals(saved, item.getOrder());

        verify(cartService).clearCart(userId);
    }

    @Test
    void placeOrder_withCampaigns_savesOrderWithCampaignEntity() {
        CartItem cartItem = new CartItem(new Product(3L, new BigDecimal("50"), 2), 1);
        Cart cart = new Cart();
        cart.setCartItems(List.of(cartItem));
        cart.setShippingFee(new BigDecimal("0"));
        cart.setCurrentDiscount(new BigDecimal("7"));

        when(cartService.getCart(userId)).thenReturn(cart);
        Campaign campaign = mock(Campaign.class);
        when(campaign.getId()).thenReturn(99L);
        when(campaignService.applyBestCampaigns(cart)).thenReturn(List.of(campaign));

        ArgumentCaptor<OrderEntity> captor = ArgumentCaptor.forClass(OrderEntity.class);
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        orderService.placeOrder(userId);

        verify(orderRepository).save(captor.capture());
        OrderEntity saved = captor.getValue();

        assertNotNull(saved.getAppliedCampaigns());
        assertEquals(1, saved.getAppliedCampaigns().size());
        CampaignEntity ce = saved.getAppliedCampaigns().get(0);
        assertEquals(99L, ce.getCampaignId());

        assertEquals(new BigDecimal("43"), saved.getTotal());
        assertEquals(new BigDecimal("7"), saved.getDiscount());
        assertEquals(1, saved.getOrderItems().size());
        OrderItemEntity item = saved.getOrderItems().get(0);
        assertEquals(1, item.getQuantity());
        assertEquals(new BigDecimal("50"), item.getUnitPrice());
        assertEquals(saved, item.getOrder());

        verify(cartService).clearCart(userId);
    }
}

package com.app.flexcart.flexcart.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;
import com.app.flexcart.flexcart.backend.model.entity.CampaignEntity;
import com.app.flexcart.flexcart.backend.model.entity.OrderEntity;
import com.app.flexcart.flexcart.backend.model.entity.OrderItemEntity;
import com.app.flexcart.flexcart.backend.model.entity.ProductEntity;
import com.app.flexcart.flexcart.backend.model.entity.UserEntity;
import com.app.flexcart.flexcart.backend.model.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final CartService cartService;

    private final CampaignService campaignService;

    public List<OrderEntity> getOrders(Long userId) {
        return orderRepository.findByUser_UserId(userId);
    }

    @Transactional
    public void placeOrder(Long userId) {
        var cart = cartService.getCart(userId);
        var campaign = campaignService.findBestCampaign(cart).orElseThrow();// TODO handle this exception
        var discount = campaign.calculateDiscount(cart);
        var total = cart.getTotal();
        var order = new OrderEntity();

        var campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignId(campaign.getId());

        order.setAppliedCampaign(campaignEntity);
        order.setTotal(total);
        order.setDiscount(discount);
        order.setOrderDate(LocalDateTime.now());

        var user = new UserEntity();
        user.setUserId(userId);

        order.setUser(user);
        order.setOrderItems(generateOrderItems(cart.getCartItems(), order));
        orderRepository.save(order);
        cartService.clearCart(userId);
    }

    private List<OrderItemEntity> generateOrderItems(List<CartItem> cartItems, OrderEntity order) {
        return cartItems.stream()
                .map(cartItem -> {
                    var orderItemEntity = new OrderItemEntity();
                    var product = cartItem.getProduct();
                    var productEntity = new ProductEntity();

                    productEntity.setId(product.getProductId());
                    orderItemEntity.setOrder(order);
                    orderItemEntity.setProduct(productEntity);
                    orderItemEntity.setQuantity(cartItem.getQuantity());
                    orderItemEntity.setUnitPrice(product.getPrice());
                    return orderItemEntity;
                })
                .toList();
    }
}

package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;
import com.app.flexcart.flexcart.backend.domain.transaction.Order;

public class ProductQuantityCondition implements Condition {
    private final long productId;

    public ProductQuantityCondition(long productId) {
        this.productId = productId;
    }

    @Override
    public boolean isSatisfiedBy(Order order) {
        var cart = order.getCart();
        int sum = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getProductId().equals(productId))
                .mapToInt(CartItem::getQuantity)
                .sum();
        return sum > 3;
    }
}
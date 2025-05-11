package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;
import com.app.flexcart.flexcart.backend.domain.transaction.Order;

public class ProductQuantityCondition implements Condition {
    private final long productId;
    private final int minQuantity;

    public ProductQuantityCondition(long productId, int minQuantity) {
        this.minQuantity = minQuantity;
        this.productId = productId;
    }

    @Override
    public boolean isSatisfiedBy(Order order) {
        var cart = order.getCart();
        int sum = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getProductId().equals(productId))
                .mapToInt(CartItem::getQuantity)
                .sum();
        return sum > minQuantity;
    }
}
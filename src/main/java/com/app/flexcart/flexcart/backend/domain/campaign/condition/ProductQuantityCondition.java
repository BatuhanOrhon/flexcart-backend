package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;

public class ProductQuantityCondition implements Condition {
    private final long productId;
    private final int minQuantity;

    public ProductQuantityCondition(long productId, int minQuantity) {
        this.minQuantity = minQuantity;
        this.productId = productId;
    }

    @Override
    public boolean isSatisfiedBy(Cart cart) {
        int sum = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getProductId().equals(productId))
                .mapToInt(CartItem::getQuantity)
                .sum();
        return sum > minQuantity;
    }
}
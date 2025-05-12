package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;

public class CategoryQuantityCondition implements Condition {
    private final int categoryId;

    private final int minQuantity;

    public CategoryQuantityCondition(int categoryId, int minQuantity) {
        this.minQuantity = minQuantity;
        this.categoryId = categoryId;
    }

    @Override
    public boolean isSatisfiedBy(Cart cart) {
        int sum = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getCategoryId() == categoryId)
                .mapToInt(CartItem::getQuantity)
                .sum();
        return sum > minQuantity;
    }
}
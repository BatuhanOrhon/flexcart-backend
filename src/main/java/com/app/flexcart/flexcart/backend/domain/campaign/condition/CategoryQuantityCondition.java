package com.app.flexcart.flexcart.backend.domain.campaign.condition;

import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;
import com.app.flexcart.flexcart.backend.domain.transaction.Order;

public class CategoryQuantityCondition implements Condition {
    private final int categoryId;

    private final int minQuantity;

    public CategoryQuantityCondition(int categoryId, int minQuantity) {
        this.minQuantity = minQuantity;
        this.categoryId = categoryId;
    }

    @Override
    public boolean isSatisfiedBy(Order order) {
        var cart = order.getCart();
        int sum = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getCategoryId() == categoryId)
                .mapToInt(CartItem::getQuantity)
                .sum();
        return sum > minQuantity;
    }
}
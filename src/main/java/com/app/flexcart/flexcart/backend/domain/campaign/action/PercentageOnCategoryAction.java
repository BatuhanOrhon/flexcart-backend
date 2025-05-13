package com.app.flexcart.flexcart.backend.domain.campaign.action;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;

public class PercentageOnCategoryAction implements Action {
    private final int categoryId;
    private final BigDecimal percent;

    public PercentageOnCategoryAction(int categoryId, BigDecimal pct) {
        this.categoryId = categoryId;
        this.percent = pct;
    }

    public BigDecimal apply(Cart cart) {
        BigDecimal totalSub = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getCategoryId() == categoryId)
                .map(CartItem::getSubTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CartItem> list = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getCategoryId() == categoryId).toList();
        return totalSub
                .multiply(percent)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
    }
}

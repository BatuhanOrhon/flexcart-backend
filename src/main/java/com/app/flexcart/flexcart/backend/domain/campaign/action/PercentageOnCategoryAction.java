package com.app.flexcart.flexcart.backend.domain.campaign.action;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;

import lombok.Getter;

@Getter
public class PercentageOnCategoryAction implements Action {
    private final int categoryId;
    private final BigDecimal percent;

    public PercentageOnCategoryAction(int categoryId, BigDecimal pct) {
        this.categoryId = categoryId;
        this.percent = pct;
    }

    public BigDecimal calculate(Cart cart) {
        BigDecimal totalSub = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getCategoryId() == categoryId)
                .map(CartItem::getSubTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalSub
                .multiply(percent)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
    }

    @Override
    public void apply(Cart cart) {
        cart.setCurrentDiscount(cart.getCurrentDiscount().add(calculate(cart)));
    }
}

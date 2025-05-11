package com.app.flexcart.flexcart.backend.domain.campaign.action;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;
import com.app.flexcart.flexcart.backend.domain.transaction.Order;

public class PercentageOnProductAction implements Action {
    private final Long productId;
    private final BigDecimal percent;

    public PercentageOnProductAction(Long productId, BigDecimal percent) {
        this.percent = percent;
        this.productId = productId;
    }

    @Override
    public BigDecimal apply(Order order) {
        var cart = order.getCart();
        BigDecimal totalSub = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getProductId().equals(productId))
                .map(CartItem::getSubTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalSub
                .multiply(percent)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.UNNECESSARY);
    }
}

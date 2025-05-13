package com.app.flexcart.flexcart.backend.domain.campaign.action;

import java.math.BigDecimal;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

public class FixedAmountOnCategoryAction implements Action {
    private final int categoryId;
    private final BigDecimal amount;

    public FixedAmountOnCategoryAction(int categoryId, BigDecimal amount) {
        this.categoryId = categoryId;
        this.amount = amount;
    }

    public void apply(Cart cart) {
        cart.setCurrentDiscount(cart.getCurrentDiscount().add(calculate(cart)));
    }

    public BigDecimal calculate(Cart cart) {
        var totalPriceOfCategory = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getCategoryId() == categoryId)
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPriceOfCategory.compareTo(amount) > 0) {
            return amount;
        } else {
            return totalPriceOfCategory;
        }
    }
}

package com.app.flexcart.flexcart.backend.domain.campaign.action;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.app.flexcart.flexcart.backend.domain.transaction.Order;

public class FreeUnitsOnCategoryAction implements Action {
    private final int categoryId;
    private final int numberOfFreeProducts;

    public FreeUnitsOnCategoryAction(int categoryId, int numberOfFreeProducts) {
        this.categoryId = categoryId;
        this.numberOfFreeProducts = numberOfFreeProducts;
    }

    @Override
    public BigDecimal apply(Order order) {
        var cart = order.getCart();

        List<BigDecimal> unitPrices = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getCategoryId() == categoryId)
                .flatMap(item -> Collections.nCopies(item.getQuantity(), item.getProduct().getPrice()).stream())
                .sorted(BigDecimal::compareTo)
                .collect(Collectors.toList());

        int freebies = Math.min(numberOfFreeProducts, unitPrices.size());

        return unitPrices.stream()
                .limit(freebies)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}

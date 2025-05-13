package com.app.flexcart.flexcart.backend.domain.campaign.action;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import lombok.Getter;

@Getter
public class FreeUnitsOnCategoryAction implements Action {
    private final int categoryId;
    private final int freeUnits;

    public FreeUnitsOnCategoryAction(int categoryId, int freeUnits) {
        this.categoryId = categoryId;
        this.freeUnits = freeUnits;
    }

    @Override
    public BigDecimal apply(Cart cart) {
        List<BigDecimal> unitPrices = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getCategoryId() == categoryId)
                .flatMap(item -> Collections.nCopies(item.getQuantity(), item.getProduct().getPrice()).stream())
                .sorted(BigDecimal::compareTo)
                .collect(Collectors.toList());

        int freebies = Math.min(freeUnits, unitPrices.size());

        return unitPrices.stream()
                .limit(freebies)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}

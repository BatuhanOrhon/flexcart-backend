package com.app.flexcart.flexcart.backend.domain.campaign;

import java.math.BigDecimal;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

public interface Campaign {
    String getName();

    String getDescription();

    String getStartDate();

    String getEndDate();

    BigDecimal apply(Cart cart);
}

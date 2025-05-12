package com.app.flexcart.flexcart.backend.domain.transaction;

import java.math.BigDecimal;
import java.util.List;

import com.app.flexcart.flexcart.backend.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {
    List<CartItem> cartItems;
    User user;
    BigDecimal shippingFee;

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            total = total.add(item.getSubTotalPrice());
        }
        return total.add(shippingFee);
    }

}

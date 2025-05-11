package com.app.flexcart.flexcart.backend.domain.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    User user;
    Cart cart;
    Campaign appliedCampaign;
    BigDecimal total;
    BigDecimal discount;
    LocalDateTime orderDate;
}

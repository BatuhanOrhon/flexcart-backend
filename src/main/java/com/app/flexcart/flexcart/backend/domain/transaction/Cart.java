package com.app.flexcart.flexcart.backend.domain.transaction;

import java.util.List;

import com.app.flexcart.flexcart.backend.domain.campaign.Campaign;
import com.app.flexcart.flexcart.backend.domain.user.User;

public class Cart {
    User user;
    List<Item> cartItems;
    Campaign appliedCampaign;
}

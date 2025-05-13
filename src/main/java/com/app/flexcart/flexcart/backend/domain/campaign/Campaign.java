package com.app.flexcart.flexcart.backend.domain.campaign;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.app.flexcart.flexcart.backend.domain.campaign.action.Action;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.Condition;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Campaign {
    private Long id;
    private String name;
    private String description;
    private List<Condition> conditions;
    private List<Action> actions;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CampaignType type;

    public void applyDiscount(Cart cart) {
        actions.stream().forEach(a -> a.apply(cart));
    }

    public BigDecimal calculateDiscount(Cart cart) {
        return actions.stream()
                .map(a -> a.calculate(cart))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isApplicable(Cart cart) {
        return conditions.stream().allMatch(cond -> cond.isSatisfiedBy(cart));
    }
}

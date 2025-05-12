package com.app.flexcart.flexcart.backend.domain.campaign;

import java.math.BigDecimal;
import java.util.List;

import com.app.flexcart.flexcart.backend.domain.campaign.action.Action;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.Condition;
import com.app.flexcart.flexcart.backend.domain.transaction.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Campaign {
    private String name;
    private String description;
    private List<Condition> conditions;
    private List<Action> actions;

    public BigDecimal calculateDiscount(Order order) {
        return actions.stream()
                .map(a -> a.apply(order))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isApplicable(Order order) {
        return conditions.stream().allMatch(cond -> cond.isSatisfiedBy(order));
    }
}

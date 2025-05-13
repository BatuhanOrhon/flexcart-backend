package com.app.flexcart.flexcart.backend.controller.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.app.flexcart.flexcart.backend.controller.schema.OrderResponse;
import com.app.flexcart.flexcart.backend.controller.schema.subclasses.OrderItemResponse;
import com.app.flexcart.flexcart.backend.model.entity.OrderEntity;

@Component
public class OrderResponseGenerator {
    public List<OrderResponse> generateOrderResponseList(List<OrderEntity> orders) {
        return orders.stream()
                .map(o -> new OrderResponse(o.getId(), o.getOrderDate(), o.getTotal(), o.getDiscount(),
                        o.getOrderItems().stream()
                                .map(i -> new OrderItemResponse(i.getProduct().getId(), i.getQuantity(),
                                        i.getProduct().getPrice()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}

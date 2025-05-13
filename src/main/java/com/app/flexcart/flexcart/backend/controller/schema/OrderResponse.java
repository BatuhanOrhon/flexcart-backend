package com.app.flexcart.flexcart.backend.controller.schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.app.flexcart.flexcart.backend.controller.schema.subclasses.OrderItemResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private List<OrderItemResponse> items;
}

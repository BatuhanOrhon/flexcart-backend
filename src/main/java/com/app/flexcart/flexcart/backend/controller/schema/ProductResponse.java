package com.app.flexcart.flexcart.backend.controller.schema;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    Long id;
    BigDecimal price;
    String categoryName;
    String name;
}

package com.app.flexcart.flexcart.backend.controller.schema;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Response object for product details")
public class ProductResponse {

    @Schema(description = "ID of the product", example = "101", required = true)
    private Long id;

    @Schema(description = "Price of the product", example = "99.99", required = true)
    private BigDecimal price;

    @Schema(description = "Name of the category the product belongs to", example = "Electronics", required = true)
    private String categoryName;

    @Schema(description = "Name of the product", example = "Wireless Mouse", required = true)
    private String name;
}

package com.app.flexcart.flexcart.backend.controller.schema;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Request object for creating a new product")
public class PostProductRequest {

    @NotNull
    @Schema(description = "Price of the product", example = "99.99", required = true)
    private BigDecimal price;

    @NotNull
    @Schema(description = "ID of the category the product belongs to", example = "1", required = true)
    private Long categoryId;

    @NotNull
    @Schema(description = "Name of the product", example = "Wireless Mouse", required = true)
    private String name;
}

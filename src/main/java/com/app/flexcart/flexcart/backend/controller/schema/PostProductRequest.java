package com.app.flexcart.flexcart.backend.controller.schema;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostProductRequest {
    @NotNull
    BigDecimal price;
    @NotNull
    Long categoryId;
    @NotNull
    String name;
}

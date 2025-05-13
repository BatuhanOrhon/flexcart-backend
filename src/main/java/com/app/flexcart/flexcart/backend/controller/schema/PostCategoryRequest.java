package com.app.flexcart.flexcart.backend.controller.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request object for creating a new category")
public class PostCategoryRequest {
    @NotNull
    @Schema(description = "Name of the category", example = "Electronics", required = true)
    private String name;
}

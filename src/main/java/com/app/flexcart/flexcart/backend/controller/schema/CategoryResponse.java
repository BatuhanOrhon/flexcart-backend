package com.app.flexcart.flexcart.backend.controller.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Response object for category details")
public class CategoryResponse {
    @Schema(description = "ID of the category", example = "1", required = true)
    private Long id;

    @Schema(description = "Name of the category", example = "Electronics", required = true)
    private String name;

}

package com.app.flexcart.flexcart.backend.controller.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request object for creating a new user")
public class PostUserRequest {
    @NotNull
    @NotBlank
    @Schema(description = "First name of the user", example = "John", required = true)
    private String name;

    @NotNull
    @NotBlank
    @Schema(description = "Last name of the user", example = "Doe", required = true)
    private String surname;
}

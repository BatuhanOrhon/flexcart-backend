package com.app.flexcart.flexcart.backend.controller.schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String surname;
}

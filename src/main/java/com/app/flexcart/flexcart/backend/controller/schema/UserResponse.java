package com.app.flexcart.flexcart.backend.controller.schema;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    @Schema(description = "ID of the user", example = "1", required = true)
    private Long id;
    @Schema(description = "Name of the user", example = "John", required = true)
    private String name;
    @Schema(description = "Surname of the user", example = "Doe", required = true)
    private String surname;
    @Schema(description = "Registration date of the user", example = "2023-10-01T10:00:00", required = true)
    private LocalDateTime registerDate;
}

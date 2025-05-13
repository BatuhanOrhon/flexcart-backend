package com.app.flexcart.flexcart.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.IUserController;
import com.app.flexcart.flexcart.backend.controller.schema.PostUserRequest;
import com.app.flexcart.flexcart.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "User Management", description = "APIs for managing users")
@RequiredArgsConstructor
@RestController
public class UserControllerImpl implements IUserController {
    private final UserService userService;

    @Override
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<String> createUser(PostUserRequest user) {
        Long userId = userService.saveUser(user.getName(), user.getSurname());
        return ResponseEntity.ok("User is created successfully with ID: " + userId);
    }

}

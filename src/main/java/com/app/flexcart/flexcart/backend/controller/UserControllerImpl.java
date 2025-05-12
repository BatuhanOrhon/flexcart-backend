package com.app.flexcart.flexcart.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.IUserController;
import com.app.flexcart.flexcart.backend.controller.schema.PostUserRequest;
import com.app.flexcart.flexcart.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserControllerImpl implements IUserController {
    private final UserService userService;

    @Override
    public ResponseEntity<String> createUser(PostUserRequest user) {
        userService.saveUser(user.getName(), user.getSurname());
        return ResponseEntity.ok("User is created successfully");
    }

}

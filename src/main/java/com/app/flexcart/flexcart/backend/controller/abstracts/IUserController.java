package com.app.flexcart.flexcart.backend.controller.abstracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.flexcart.flexcart.backend.controller.schema.CreateUserRequest;

@RequestMapping("/api")
public interface IUserController {
    @PostMapping("/user")
    ResponseEntity<String> createUser(@RequestBody CreateUserRequest user);
}

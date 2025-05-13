package com.app.flexcart.flexcart.backend.controller.abstracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.flexcart.flexcart.backend.controller.schema.PostProductRequest;

import jakarta.validation.Valid;

@RequestMapping("/api")
public interface IProductController {
    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@Valid @RequestBody PostProductRequest product);

}

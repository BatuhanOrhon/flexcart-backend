package com.app.flexcart.flexcart.backend.controller.abstracts;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.flexcart.flexcart.backend.controller.schema.PostProductRequest;
import com.app.flexcart.flexcart.backend.controller.schema.ProductResponse;

import jakarta.validation.Valid;

@RequestMapping("/api")
public interface IProductController {
    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@Valid @RequestBody PostProductRequest product);

    @GetMapping("/product")
    public ResponseEntity<List<ProductResponse>> getAllProducts();

}

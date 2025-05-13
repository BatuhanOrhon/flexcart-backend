package com.app.flexcart.flexcart.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.IProductController;
import com.app.flexcart.flexcart.backend.controller.schema.PostProductRequest;
import com.app.flexcart.flexcart.backend.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductControllerImpl implements IProductController {
    private final ProductService productService;

    @Override
    public ResponseEntity<String> createProduct(PostProductRequest product) {
        productService.saveProduct(product.getName(), product.getPrice(),
                product.getCategoryId());
        return ResponseEntity.ok("Product is created successfully");
    }

}

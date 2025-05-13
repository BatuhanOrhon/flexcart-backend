package com.app.flexcart.flexcart.backend.service;

import org.springframework.stereotype.Service;

import com.app.flexcart.flexcart.backend.exception.ProductNotFoundException;
import com.app.flexcart.flexcart.backend.model.entity.ProductEntity;
import com.app.flexcart.flexcart.backend.model.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class ProductService {
    private final ProductRepository productRepository;

    public ProductEntity getProductEntityById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }
}
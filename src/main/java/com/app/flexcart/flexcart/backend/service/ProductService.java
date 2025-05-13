package com.app.flexcart.flexcart.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.flexcart.flexcart.backend.controller.schema.ProductResponse;
import com.app.flexcart.flexcart.backend.exception.ProductNotFoundException;
import com.app.flexcart.flexcart.backend.model.entity.CategoryEntity;
import com.app.flexcart.flexcart.backend.model.entity.ProductEntity;
import com.app.flexcart.flexcart.backend.model.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void saveProduct(String name, BigDecimal price, long categoryId) {
        var entity = new ProductEntity();
        entity.setProductName(name);
        entity.setPrice(price);
        var category = new CategoryEntity();
        category.setId(categoryId);
        entity.setCategory(category);
        productRepository.save(entity);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(product -> {
            var response = new ProductResponse();
            response.setId(product.getId());
            response.setName(product.getProductName());
            response.setPrice(product.getPrice());
            response.setCategoryName(product.getCategory().getName());
            return response;
        }).toList();
    }

    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product not found with id %d", id)));
    }
}
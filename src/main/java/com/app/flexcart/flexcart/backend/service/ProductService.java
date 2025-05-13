package com.app.flexcart.flexcart.backend.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

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
}
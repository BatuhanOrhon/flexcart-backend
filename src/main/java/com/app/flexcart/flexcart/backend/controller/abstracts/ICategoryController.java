package com.app.flexcart.flexcart.backend.controller.abstracts;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.flexcart.flexcart.backend.controller.schema.CategoryResponse;
import com.app.flexcart.flexcart.backend.controller.schema.PostCategoryRequest;

import jakarta.validation.Valid;

@RequestMapping("/api")
public interface ICategoryController {
    @PostMapping("/category")
    public ResponseEntity<String> createCategory(@Valid @RequestBody PostCategoryRequest category);

    @GetMapping("/category")
    public ResponseEntity<List<CategoryResponse>> getAllCategories();
}

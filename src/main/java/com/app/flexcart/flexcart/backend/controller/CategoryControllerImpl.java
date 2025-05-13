package com.app.flexcart.flexcart.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.ICategoryController;
import com.app.flexcart.flexcart.backend.controller.schema.CategoryResponse;
import com.app.flexcart.flexcart.backend.controller.schema.PostCategoryRequest;
import com.app.flexcart.flexcart.backend.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Category", description = "Category management APIs")
@RequiredArgsConstructor
@RestController
public class CategoryControllerImpl implements ICategoryController {

    private final CategoryService categoryService;

    @Override
    @Operation(summary = "Create a new category", description = "Creates a new category with the provided name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category is created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<String> createCategory(PostCategoryRequest category) {
        categoryService.createCategory(category.getName());
        return ResponseEntity.ok("Category is created successfully");
    }

    @Override
    @Operation(summary = "Get all categories", description = "Fetches a list of all available categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No categories found")
    })
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        var categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }

}

package com.app.flexcart.flexcart.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.controller.abstracts.IProductController;
import com.app.flexcart.flexcart.backend.controller.schema.PostProductRequest;
import com.app.flexcart.flexcart.backend.controller.schema.ProductResponse;
import com.app.flexcart.flexcart.backend.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Product Management", description = "APIs for managing products")
@RequiredArgsConstructor
@RestController
public class ProductControllerImpl implements IProductController {
    private final ProductService productService;

    @Override
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product is created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<String> createProduct(PostProductRequest product) {
        productService.saveProduct(product.getName(), product.getPrice(),
                product.getCategoryId());
        return ResponseEntity.ok("Product is created successfully");
    }

    @Override
    @Operation(summary = "Get all products", description = "Fetches a list of all available products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No products found")
    })
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        var productList = productService.getAllProducts();
        if (productList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(productList);

    }

}

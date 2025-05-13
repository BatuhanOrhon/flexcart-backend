package com.app.flexcart.flexcart.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;

import com.app.flexcart.flexcart.backend.controller.schema.ProductResponse;
import com.app.flexcart.flexcart.backend.model.entity.CategoryEntity;
import com.app.flexcart.flexcart.backend.model.entity.ProductEntity;
import com.app.flexcart.flexcart.backend.model.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveProduct_shouldCreateEntityAndSave() {
        String name = "TestProduct";
        BigDecimal price = new BigDecimal("123.45");
        long categoryId = 7L;

        ArgumentCaptor<ProductEntity> captor = ArgumentCaptor.forClass(ProductEntity.class);
        when(productRepository.save(any(ProductEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        productService.saveProduct(name, price, categoryId);

        verify(productRepository).save(captor.capture());
        ProductEntity saved = captor.getValue();

        assertEquals(name, saved.getProductName());
        assertEquals(price, saved.getPrice());
        assertNotNull(saved.getCategory());
        assertEquals(categoryId, saved.getCategory().getId());
    }

    @Test
    void getAllProducts_shouldMapEntitiesToResponses() {
        ProductEntity p1 = new ProductEntity();
        p1.setId(1L);
        p1.setProductName("Prod1");
        p1.setPrice(new BigDecimal("10.00"));
        CategoryEntity c1 = new CategoryEntity();
        c1.setId(2L);
        c1.setName("CatA");
        p1.setCategory(c1);

        ProductEntity p2 = new ProductEntity();
        p2.setId(3L);
        p2.setProductName("Prod2");
        p2.setPrice(new BigDecimal("20.00"));
        CategoryEntity c2 = new CategoryEntity();
        c2.setId(4L);
        c2.setName("CatB");
        p2.setCategory(c2);

        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProductResponse> responses = productService.getAllProducts();

        assertEquals(2, responses.size());

        ProductResponse r1 = responses.get(0);
        assertEquals(1L, r1.getId());
        assertEquals("Prod1", r1.getName());
        assertEquals(new BigDecimal("10.00"), r1.getPrice());
        assertEquals("CatA", r1.getCategoryName());

        ProductResponse r2 = responses.get(1);
        assertEquals(3L, r2.getId());
        assertEquals("Prod2", r2.getName());
        assertEquals(new BigDecimal("20.00"), r2.getPrice());
        assertEquals("CatB", r2.getCategoryName());

        verify(productRepository).findAll();
    }
}
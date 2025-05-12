package com.app.flexcart.flexcart.backend.domain.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.action.FreeUnitsOnCategoryAction;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;
import com.app.flexcart.flexcart.backend.domain.transaction.Product;

public class FreeUnitsOnCategoryActionTest {

    @Test
    public void testApply_SingleProduct_LessThanFreeUnits() {
        int categoryId = 1;
        int numberOfFreeProducts = 5;
        FreeUnitsOnCategoryAction action = new FreeUnitsOnCategoryAction(categoryId, numberOfFreeProducts);

        Product mockProduct = mock(Product.class);
        when(mockProduct.getCategoryId()).thenReturn(categoryId);
        when(mockProduct.getPrice()).thenReturn(new BigDecimal("50.00"));

        CartItem mockCartItem = mock(CartItem.class);
        when(mockCartItem.getProduct()).thenReturn(mockProduct);
        when(mockCartItem.getQuantity()).thenReturn(3);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getCartItems()).thenReturn(List.of(mockCartItem));

        BigDecimal result = action.apply(mockCart);

        assertEquals(new BigDecimal("150.00"), result);
    }

    @Test
    public void testApply_SingleProduct_MoreThanFreeUnits() {
        int categoryId = 1;
        int numberOfFreeProducts = 2;
        FreeUnitsOnCategoryAction action = new FreeUnitsOnCategoryAction(categoryId, numberOfFreeProducts);

        Product mockProduct = mock(Product.class);
        when(mockProduct.getCategoryId()).thenReturn(categoryId);
        when(mockProduct.getPrice()).thenReturn(new BigDecimal("50.00"));

        CartItem mockCartItem = mock(CartItem.class);
        when(mockCartItem.getProduct()).thenReturn(mockProduct);
        when(mockCartItem.getQuantity()).thenReturn(5);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getCartItems()).thenReturn(List.of(mockCartItem));

        BigDecimal result = action.apply(mockCart);

        assertEquals(new BigDecimal("100.00"), result);
    }

    @Test
    public void testApply_MultipleProducts_LessThanFreeUnits() {
        int categoryId = 1;
        int numberOfFreeProducts = 5;
        FreeUnitsOnCategoryAction action = new FreeUnitsOnCategoryAction(categoryId, numberOfFreeProducts);

        Product mockProduct1 = mock(Product.class);
        when(mockProduct1.getCategoryId()).thenReturn(categoryId);
        when(mockProduct1.getPrice()).thenReturn(new BigDecimal("50.00"));

        Product mockProduct2 = mock(Product.class);
        when(mockProduct2.getCategoryId()).thenReturn(categoryId);
        when(mockProduct2.getPrice()).thenReturn(new BigDecimal("30.00"));

        CartItem mockCartItem1 = mock(CartItem.class);
        when(mockCartItem1.getProduct()).thenReturn(mockProduct1);
        when(mockCartItem1.getQuantity()).thenReturn(2);

        CartItem mockCartItem2 = mock(CartItem.class);
        when(mockCartItem2.getProduct()).thenReturn(mockProduct2);
        when(mockCartItem2.getQuantity()).thenReturn(2);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getCartItems()).thenReturn(List.of(mockCartItem1, mockCartItem2));

        BigDecimal result = action.apply(mockCart);

        assertEquals(new BigDecimal("160.00"), result);
    }

    @Test
    public void testApply_MultipleProducts_MoreThanFreeUnits() {
        int categoryId = 1;
        int numberOfFreeProducts = 3;
        FreeUnitsOnCategoryAction action = new FreeUnitsOnCategoryAction(categoryId, numberOfFreeProducts);

        Product mockProduct1 = mock(Product.class);
        when(mockProduct1.getCategoryId()).thenReturn(categoryId);
        when(mockProduct1.getPrice()).thenReturn(new BigDecimal("50.00"));

        Product mockProduct2 = mock(Product.class);
        when(mockProduct2.getCategoryId()).thenReturn(categoryId);
        when(mockProduct2.getPrice()).thenReturn(new BigDecimal("30.00"));

        CartItem mockCartItem1 = mock(CartItem.class);
        when(mockCartItem1.getProduct()).thenReturn(mockProduct1);
        when(mockCartItem1.getQuantity()).thenReturn(2);

        CartItem mockCartItem2 = mock(CartItem.class);
        when(mockCartItem2.getProduct()).thenReturn(mockProduct2);
        when(mockCartItem2.getQuantity()).thenReturn(2);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getCartItems()).thenReturn(List.of(mockCartItem1, mockCartItem2));

        BigDecimal result = action.apply(mockCart);

        assertEquals(new BigDecimal("110.00"), result);
    }
}

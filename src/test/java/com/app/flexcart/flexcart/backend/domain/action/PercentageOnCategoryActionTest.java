package com.app.flexcart.flexcart.backend.domain.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.action.PercentageOnCategoryAction;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;
import com.app.flexcart.flexcart.backend.domain.transaction.Product;

public class PercentageOnCategoryActionTest {

    @Test
    public void testApply_PercentageAppliedCorrectly() {
        int categoryId = 1;
        BigDecimal percent = new BigDecimal("10.00");
        PercentageOnCategoryAction action = new PercentageOnCategoryAction(categoryId, percent);

        Product mockProduct = mock(Product.class);
        when(mockProduct.getCategoryId()).thenReturn(categoryId);

        CartItem mockCartItem1 = mock(CartItem.class);
        when(mockCartItem1.getProduct()).thenReturn(mockProduct);
        when(mockCartItem1.getSubTotalPrice()).thenReturn(new BigDecimal("100.00"));

        CartItem mockCartItem2 = mock(CartItem.class);
        when(mockCartItem2.getProduct()).thenReturn(mockProduct);
        when(mockCartItem2.getSubTotalPrice()).thenReturn(new BigDecimal("50.00"));

        Cart mockCart = mock(Cart.class);
        when(mockCart.getCartItems()).thenReturn(List.of(mockCartItem1, mockCartItem2));

        BigDecimal result = action.calculate(mockCart);

        assertEquals(new BigDecimal("15.00"), result);
    }

    @Test
    public void testApply_NoMatchingCategory() {
        int categoryId = 1;
        BigDecimal percent = new BigDecimal("10.00");
        PercentageOnCategoryAction action = new PercentageOnCategoryAction(categoryId, percent);

        Product mockProduct = mock(Product.class);
        when(mockProduct.getCategoryId()).thenReturn(2);

        CartItem mockCartItem = mock(CartItem.class);
        when(mockCartItem.getProduct()).thenReturn(mockProduct);
        when(mockCartItem.getSubTotalPrice()).thenReturn(new BigDecimal("100.00"));

        Cart mockCart = mock(Cart.class);
        when(mockCart.getCartItems()).thenReturn(List.of(mockCartItem));

        BigDecimal result = action.calculate(mockCart);

        assertEquals(BigDecimal.valueOf(0, 2), result);
    }

    @Test
    public void testApply_ZeroPercent() {
        int categoryId = 1;
        BigDecimal percent = BigDecimal.ZERO;
        PercentageOnCategoryAction action = new PercentageOnCategoryAction(categoryId, percent);

        Product mockProduct = mock(Product.class);
        when(mockProduct.getCategoryId()).thenReturn(categoryId);

        CartItem mockCartItem = mock(CartItem.class);
        when(mockCartItem.getProduct()).thenReturn(mockProduct);
        when(mockCartItem.getSubTotalPrice()).thenReturn(new BigDecimal("100.00"));

        Cart mockCart = mock(Cart.class);
        when(mockCart.getCartItems()).thenReturn(List.of(mockCartItem));

        BigDecimal result = action.calculate(mockCart);

        assertEquals(BigDecimal.valueOf(0, 2), result);
    }
}

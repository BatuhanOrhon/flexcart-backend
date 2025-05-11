package com.app.flexcart.flexcart.backend.domain.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.condition.CategoryQuantityCondition;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;
import com.app.flexcart.flexcart.backend.domain.transaction.Order;
import com.app.flexcart.flexcart.backend.domain.transaction.Product;

public class CategoryQuantityConditionTest {
    @Test
    public void testIsSatisfiedBy_ConditionMet() {
        int categoryId = 1;
        CategoryQuantityCondition condition = new CategoryQuantityCondition(categoryId);

        Product mockProduct = mock(Product.class);
        when(mockProduct.getCategoryId()).thenReturn(categoryId);

        CartItem mockCartItem1 = mock(CartItem.class);
        when(mockCartItem1.getProduct()).thenReturn(mockProduct);
        when(mockCartItem1.getQuantity()).thenReturn(2);

        CartItem mockCartItem2 = mock(CartItem.class);
        when(mockCartItem2.getProduct()).thenReturn(mockProduct);
        when(mockCartItem2.getQuantity()).thenReturn(2);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getCartItems()).thenReturn(List.of(mockCartItem1, mockCartItem2));

        Order mockOrder = mock(Order.class);
        when(mockOrder.getCart()).thenReturn(mockCart);

        boolean result = condition.isSatisfiedBy(mockOrder);

        assertTrue(result, "Condition should be satisfied when total quantity in the category exceeds 3.");
    }

    @Test
    public void testIsSatisfiedBy_ConditionNotMet() {
        int categoryId = 1;
        CategoryQuantityCondition condition = new CategoryQuantityCondition(categoryId);

        Product mockProduct = mock(Product.class);
        when(mockProduct.getCategoryId()).thenReturn(categoryId);

        CartItem mockCartItem = mock(CartItem.class);
        when(mockCartItem.getProduct()).thenReturn(mockProduct);
        when(mockCartItem.getQuantity()).thenReturn(2);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getCartItems()).thenReturn(List.of(mockCartItem));

        Order mockOrder = mock(Order.class);
        when(mockOrder.getCart()).thenReturn(mockCart);

        boolean result = condition.isSatisfiedBy(mockOrder);

        assertFalse(result, "Condition should not be satisfied when total quantity in the category is 3 or less.");
    }

    @Test
    public void testIsSatisfiedBy_NoMatchingCategory() {
        int categoryId = 1;
        CategoryQuantityCondition condition = new CategoryQuantityCondition(categoryId);

        Product mockProduct = mock(Product.class);
        when(mockProduct.getCategoryId()).thenReturn(2); // Different category ID

        CartItem mockCartItem = mock(CartItem.class);
        when(mockCartItem.getProduct()).thenReturn(mockProduct);
        when(mockCartItem.getQuantity()).thenReturn(5);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getCartItems()).thenReturn(List.of(mockCartItem));

        Order mockOrder = mock(Order.class);
        when(mockOrder.getCart()).thenReturn(mockCart);

        boolean result = condition.isSatisfiedBy(mockOrder);

        assertFalse(result, "Condition should not be satisfied when no items match the category.");
    }
}

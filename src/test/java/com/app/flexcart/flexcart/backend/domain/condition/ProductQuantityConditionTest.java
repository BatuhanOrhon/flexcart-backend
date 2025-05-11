package com.app.flexcart.flexcart.backend.domain.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.app.flexcart.flexcart.backend.domain.campaign.condition.ProductQuantityCondition;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;
import com.app.flexcart.flexcart.backend.domain.transaction.Order;
import com.app.flexcart.flexcart.backend.domain.transaction.Product;

public class ProductQuantityConditionTest {

    @Test
    public void testIsSatisfiedBy_ConditionMet() {
        long productId = 1L;
        ProductQuantityCondition condition = new ProductQuantityCondition(productId);

        Product mockProduct = mock(Product.class);
        when(mockProduct.getProductId()).thenReturn(productId);

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

        assertTrue(result);
    }

    @Test
    public void testIsSatisfiedBy_ConditionNotMet() {
        long productId = 1L;
        ProductQuantityCondition condition = new ProductQuantityCondition(productId);

        Product mockProduct = mock(Product.class);
        when(mockProduct.getProductId()).thenReturn(productId);

        CartItem mockCartItem = mock(CartItem.class);
        when(mockCartItem.getProduct()).thenReturn(mockProduct);
        when(mockCartItem.getQuantity()).thenReturn(2);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getCartItems()).thenReturn(List.of(mockCartItem));

        Order mockOrder = mock(Order.class);
        when(mockOrder.getCart()).thenReturn(mockCart);

        boolean result = condition.isSatisfiedBy(mockOrder);

        assertFalse(result);
    }

    @Test
    public void testIsSatisfiedBy_NoMatchingProduct() {
        long productId = 1L;
        ProductQuantityCondition condition = new ProductQuantityCondition(productId);

        Product mockProduct = mock(Product.class);
        when(mockProduct.getProductId()).thenReturn(2L);

        CartItem mockCartItem = mock(CartItem.class);
        when(mockCartItem.getProduct()).thenReturn(mockProduct);
        when(mockCartItem.getQuantity()).thenReturn(5);

        Cart mockCart = mock(Cart.class);
        when(mockCart.getCartItems()).thenReturn(List.of(mockCartItem));

        Order mockOrder = mock(Order.class);
        when(mockOrder.getCart()).thenReturn(mockCart);

        boolean result = condition.isSatisfiedBy(mockOrder);

        assertFalse(result);
    }
}

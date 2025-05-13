package com.app.flexcart.flexcart.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.flexcart.flexcart.backend.controller.util.CampaignResponseGenerator;
import com.app.flexcart.flexcart.backend.controller.util.CartItemResponseGenerator;
import com.app.flexcart.flexcart.backend.exception.CartNotFoundException;
import com.app.flexcart.flexcart.backend.model.entity.CartEntity;
import com.app.flexcart.flexcart.backend.model.entity.CartItemEntity;
import com.app.flexcart.flexcart.backend.model.entity.CategoryEntity;
import com.app.flexcart.flexcart.backend.model.entity.ProductEntity;
import com.app.flexcart.flexcart.backend.model.entity.UserEntity;
import com.app.flexcart.flexcart.backend.model.repository.CartRepository;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CampaignService campaignService;

    @Mock
    private CartItemResponseGenerator cartItemResponseGenerator;

    @Mock
    private CampaignResponseGenerator campaignResponseGenerator;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartService cartService;

    private final BigDecimal shippingFee = BigDecimal.valueOf(25);

    @Test
    void getCart_notFound_throws() {
        Long userId = 1L;
        when(cartRepository.findByUser_UserId(userId)).thenReturn(Optional.empty());
        assertThrows(CartNotFoundException.class, () -> cartService.getCart(userId));
    }

    @Test
    void removeFromCart_decreaseQuantity() {
        Long userId = 1L;
        CartEntity cart = new CartEntity();
        cart.setCartItems(new ArrayList<>());
        ProductEntity p = new ProductEntity();
        p.setId(100L);
        CartItemEntity ci = new CartItemEntity();
        ci.setProduct(p);
        ci.setQuantity(5);
        cart.getCartItems().add(ci);
        when(cartRepository.findByUser_UserId(userId)).thenReturn(Optional.of(cart));

        cartService.removeFromCart(userId, 100L, 2);

        assertEquals(3, ci.getQuantity());
        verify(cartRepository).save(cart);
    }

    @Test
    void removeFromCart_removeItemWhenQuantityLessOrEqual() {
        Long userId = 1L;
        CartEntity cart = new CartEntity();
        cart.setCartItems(new ArrayList<>());
        ProductEntity p = new ProductEntity();
        p.setId(100L);
        CartItemEntity ci = new CartItemEntity();
        ci.setProduct(p);
        ci.setQuantity(2);
        cart.getCartItems().add(ci);
        when(cartRepository.findByUser_UserId(userId)).thenReturn(Optional.of(cart));

        cartService.removeFromCart(userId, 100L, 3);

        assertTrue(cart.getCartItems().isEmpty());
        verify(cartRepository).save(cart);
    }

    @Test
    void removeFromCart_productNotInCart_throws() {
        Long userId = 1L;
        CartEntity cart = new CartEntity();
        cart.setCartItems(new ArrayList<>());
        when(cartRepository.findByUser_UserId(userId)).thenReturn(Optional.of(cart));

        assertThrows(CartNotFoundException.class,
                () -> cartService.removeFromCart(userId, 99L, 1));
    }

    @Test
    void addCartItem_newCart_createsCartAndAddsItem() {
        Long userId = 1L;
        Long productId = 50L;
        int quantity = 4;

        UserEntity user = new UserEntity();
        user.setCart(null);

        ProductEntity product = new ProductEntity();
        product.setId(productId);

        when(userService.getUserEntityById(userId)).thenReturn(user);
        when(productService.getProductById(productId)).thenReturn(product);

        cartService.addCartItem(userId, productId, quantity);

        ArgumentCaptor<CartEntity> captor = ArgumentCaptor.forClass(CartEntity.class);
        verify(cartRepository).save(captor.capture());
        CartEntity savedCart = captor.getValue();

        assertNotNull(savedCart, "Cart should not be null");
        assertEquals(shippingFee, savedCart.getShippingFee(), "Shipping fee should be set correctly");
        assertEquals(1, savedCart.getCartItems().size(), "Cart should contain one item");

        CartItemEntity savedItem = savedCart.getCartItems().get(0);
        assertNotNull(savedItem.getProduct(), "Product should not be null");
        assertEquals(productId, savedItem.getProduct().getId(), "Product ID should match");
        assertEquals(quantity, savedItem.getQuantity(), "Quantity should match");
    }

    @Test
    void addCartItem_existingCartAndItem_incrementsQuantity() {
        Long userId = 1L;
        Long productId = 50L;
        int qty = 3;
        ProductEntity pEntity = new ProductEntity();
        pEntity.setId(productId);
        CartItemEntity ci = new CartItemEntity();
        ci.setProduct(pEntity);
        ci.setQuantity(2);
        CartEntity cart = new CartEntity();
        cart.setCartItems(new ArrayList<>(List.of(ci)));
        cart.setShippingFee(shippingFee);
        UserEntity user = new UserEntity();
        user.setCart(cart);
        when(userService.getUserEntityById(userId)).thenReturn(user);

        cartService.addCartItem(userId, productId, qty);

        verify(cartRepository).save(cart);
        assertEquals(5, ci.getQuantity());
    }

    @Test
    void clearCart_clearsItemsAndResetsShippingFee() {
        Long userId = 1L;
        CartEntity cart = new CartEntity();
        CartItemEntity ci = new CartItemEntity();
        ci.setQuantity(2);
        cart.setCartItems(new ArrayList<>(List.of(ci)));
        cart.setShippingFee(BigDecimal.valueOf(100));
        when(cartRepository.findByUser_UserId(userId)).thenReturn(Optional.of(cart));

        cartService.clearCart(userId);

        assertTrue(cart.getCartItems().isEmpty());
        assertEquals(shippingFee, cart.getShippingFee());
        verify(cartRepository).save(cart);
    }

    @Test
    void getCart_returnsDomainCart() {
        Long userId = 1L;
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCartItems(new ArrayList<>());
        ProductEntity p = new ProductEntity();
        p.setId(10L);
        p.setPrice(BigDecimal.valueOf(200));
        CategoryEntity cat = new CategoryEntity();
        cat.setId(7L);
        p.setCategory(cat);
        CartItemEntity ci = new CartItemEntity();
        ci.setProduct(p);
        ci.setQuantity(3);
        cartEntity.getCartItems().add(ci);
        cartEntity.setShippingFee(shippingFee);
        UserEntity uEntity = new UserEntity();
        cartEntity.setUser(uEntity);
        when(cartRepository.findByUser_UserId(userId)).thenReturn(Optional.of(cartEntity));
        var domainUser = new com.app.flexcart.flexcart.backend.domain.user.User();
        when(userService.getUserFromEntity(uEntity)).thenReturn(domainUser);

        com.app.flexcart.flexcart.backend.domain.transaction.Cart result = cartService.getCart(userId);

        assertNotNull(result);
        assertEquals(shippingFee, result.getShippingFee());
        assertEquals(domainUser, result.getUser());
        assertEquals(1, result.getCartItems().size());
        CartItemEntity actual = null;
        assertEquals(3, result.getCartItems().get(0).getQuantity());
        assertEquals(10L, result.getCartItems().get(0).getProduct().getProductId());
        assertEquals(BigDecimal.valueOf(200), result.getCartItems().get(0).getProduct().getPrice());
        assertEquals(7, result.getCartItems().get(0).getProduct().getCategoryId());
    }
}
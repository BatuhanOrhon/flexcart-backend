package com.app.flexcart.flexcart.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.app.flexcart.flexcart.backend.model.entity.CartEntity;
import com.app.flexcart.flexcart.backend.model.entity.CartItemEntity;
import com.app.flexcart.flexcart.backend.model.entity.ProductEntity;
import com.app.flexcart.flexcart.backend.model.entity.UserEntity;
import com.app.flexcart.flexcart.backend.model.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final UserService userService;

    private final ProductService productService;

    private final CartRepository cartRepository;

    public void addCartItem(Long userId, Long productId, int quantity) {
        var user = userService.getUserById(userId);
        var cart = user.getCart();
        cart = handleEmptyCart(cart, user);
        var product = productService.getProductById(productId);
        insertProductIntoCart(cart, product, quantity);
        cartRepository.save(cart);
    }

    private void insertProductIntoCart(CartEntity cart, ProductEntity product, int quantity) {
        var cartItem = new CartItemEntity();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cart.getCartItems().add(cartItem);
    }

    private CartEntity handleEmptyCart(CartEntity cart, UserEntity user) {
        if (cart == null) {
            cart = new CartEntity();
            cart.setCartItems(new ArrayList<CartItemEntity>());
            cart.setUser(user);
            cart.setCreatedAt(LocalDateTime.now());
        }
        return cart;
    }

    public CartEntity getCart(Long userId) {
        var user = userService.getUserById(userId);
        
        return user.getCart();
    }
}

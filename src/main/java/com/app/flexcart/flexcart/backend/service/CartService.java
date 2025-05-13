package com.app.flexcart.flexcart.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;
import com.app.flexcart.flexcart.backend.domain.transaction.Product;
import com.app.flexcart.flexcart.backend.exception.CartNotFoundException;
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
        var user = userService.getUserEntityById(userId);
        var cart = user.getCart();
        cart = handleEmptyCart(cart, user);
        var product = productService.getProductEntityById(productId);
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
            cart.setShippingFee(BigDecimal.valueOf(25));
        }
        return cart;
    }

    public Cart getCart(Long userId) {
        var cartEntity = cartRepository.findByUser_UserId(userId).orElseThrow(() -> new CartNotFoundException("Cart not found for user with ID: " + userId));
        var cart = generateCart(cartEntity);
        return cart;
    }

    private CartItem convertCartItemEntityToDomain(CartItemEntity cartItemEntity) {
        var productId = cartItemEntity.getProduct().getId();
        var price = cartItemEntity.getProduct().getPrice();
        var categoryId = cartItemEntity.getProduct().getCategory().getId().intValue();

        var product = new Product(productId, price, categoryId);
        return new CartItem(product, cartItemEntity.getQuantity());
    }

    private Cart generateCart(CartEntity cartEntity) {
        var cartItems = cartEntity.getCartItems().stream().map(this::convertCartItemEntityToDomain)
                .collect(Collectors.toList());
        var cart = new Cart();
        cart.setCartItems(cartItems);
        cart.setShippingFee(cartEntity.getShippingFee());
        cart.setUser(userService.getUserFromEntity(cartEntity.getUser()));
        return cart;
    }
}

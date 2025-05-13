package com.app.flexcart.flexcart.backend.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.flexcart.flexcart.backend.controller.schema.GetCartResponse;
import com.app.flexcart.flexcart.backend.controller.util.CampaignResponseGenerator;
import com.app.flexcart.flexcart.backend.controller.util.CartItemResponseGenerator;
import com.app.flexcart.flexcart.backend.domain.transaction.Cart;
import com.app.flexcart.flexcart.backend.domain.transaction.CartItem;
import com.app.flexcart.flexcart.backend.domain.transaction.Product;
import com.app.flexcart.flexcart.backend.exception.CartNotFoundException;
import com.app.flexcart.flexcart.backend.exception.UserNotFoundException;
import com.app.flexcart.flexcart.backend.model.entity.CartEntity;
import com.app.flexcart.flexcart.backend.model.entity.CartItemEntity;
import com.app.flexcart.flexcart.backend.model.entity.UserEntity;
import com.app.flexcart.flexcart.backend.model.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final UserService userService;

    private final CartRepository cartRepository;

    private final CampaignService campaignService;

    private final CartItemResponseGenerator cartItemResponseGenerator;

    private final CampaignResponseGenerator campaignResponseGenerator;

    private final ProductService productService;

    private final BigDecimal shippingFee = BigDecimal.valueOf(25);

    public GetCartResponse getCartResponse(Long userId) {
        GetCartResponse response = new GetCartResponse();
        var cart = getCart(userId);
        if (cart.getCartItems().isEmpty()) {
            return response;
        }

        var campaignList = campaignService.applyBestCampaigns(cart);

        response.setItems(cartItemResponseGenerator.generateCartItemResponseList(cart));

        response.setCampaigns(campaignResponseGenerator.generateCampaignResponseList(campaignList));
        response.setDiscount(campaignList.stream()
                .map((c) -> c.calculateDiscount(cart))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        response.setTotalPrice(cart.getTotal());
        return response;
    }

    public void removeFromCart(Long userId, Long productId, int quantity) {
        var cart = getCartEntity(userId);
        var cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new CartNotFoundException("Product not found in cart"));
        if (cartItem.getQuantity() > quantity) {
            cartItem.setQuantity(cartItem.getQuantity() - quantity);
        } else {
            cart.getCartItems().remove(cartItem);
        }
        cartRepository.save(cart);
    }

    public void addCartItem(Long userId, Long productId, int quantity) {
        var user = userService.getUserEntityById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        var cart = user.getCart();
        cart = handleEmptyCart(cart, user);
        insertProductIntoCart(cart, productId, quantity);
        cartRepository.save(cart);
    }

    private void insertProductIntoCart(CartEntity cart, Long productId, int quantity) {
        var cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        cartItem.ifPresentOrElse(item -> item.setQuantity(item.getQuantity() + quantity), () -> {
            var cartItemEntity = new CartItemEntity();
            var productEntity = productService.getProductById(productId);
            cartItemEntity.setProduct(productEntity);
            cartItemEntity.setQuantity(quantity);
            cartItemEntity.setCart(cart);
            cart.getCartItems().add(cartItemEntity);
        });

    }

    private CartEntity handleEmptyCart(CartEntity cart, UserEntity user) {
        if (cart == null) {
            cart = new CartEntity();
            cart.setCartItems(new ArrayList<CartItemEntity>());
            cart.setUser(user);
            cart.setShippingFee(shippingFee);
        }
        return cart;
    }

    public Cart getCart(Long userId) {
        var cartEntity = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user with ID: " + userId));
        var cart = generateCart(cartEntity);
        return cart;
    }

    public CartEntity getCartEntity(Long userId) {
        return cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user with ID: " + userId));

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
        cart.setCurrentDiscount(BigDecimal.ZERO);
        return cart;
    }

    public void clearCart(Long userId) {
        var cart = getCartEntity(userId);
        cart.getCartItems().clear();
        cart.setShippingFee(shippingFee);
        cartRepository.save(cart);
    }
}

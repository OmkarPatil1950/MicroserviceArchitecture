package com.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.entity.Cart;
import com.cart.entity.CartItem;
import com.cart.repository.CartRepository;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart addItemToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
        }

        CartItem item = new CartItem();
        item.setProductId(productId);
        item.setQuantity(quantity);

        cart.getItems().add(item);

        return cartRepository.save(cart);
    }

    public void removeItemFromCart(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId);

        if (cart != null) {
            cart.getItems().removeIf(item -> item.getItemId().equals(itemId));
            cartRepository.save(cart);
        }
    }
}

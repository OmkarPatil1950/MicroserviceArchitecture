package com.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.cart.dto.CartEvent;
import com.cart.entity.Cart;
import com.cart.entity.CartItem;
import com.cart.repository.CartRepository;

import java.util.Optional;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private KafkaTemplate<String, CartEvent> kafkaTemplate;

	public Cart getCartByUserId(String userId) {
		return cartRepository.findByUserId(userId);
	}

	public Cart addItemToCart(String userId, Long productId, Integer quantity) {
		Cart cart = cartRepository.findByUserId(userId);

		if (cart == null) {
			cart = new Cart();
			cart.setUserId(userId);
		}

		CartItem item = new CartItem();
		item.setProductId(productId);
		item.setQuantity(quantity);
		cart.getItems().add(item);

		cartRepository.save(cart);

		CartEvent cartEvent = new CartEvent(userId, productId, quantity);
		kafkaTemplate.send("cartTopic", cartEvent);

		return cart;
	}

	public void removeItemFromCart(String userId, Long itemId) {
		Cart cart = cartRepository.findByUserId(userId);

		if (cart != null) {
			cart.getItems().removeIf(item -> item.getItemId().equals(itemId));
			cartRepository.save(cart);
		}
	}
	
	
	@KafkaListener(topics = "inventoryFailureTopic", groupId = "cart-group")
	public void handleInventoryFailure(Long event) {
	    System.out.println("Inventory failure received for product ID: " + event);

	    // Rollback the cart: remove the item from the cart
	    Optional<Cart> optionalCart = cartRepository.findById(event); // Assuming 'event.getCartId()' gives the cart ID
	   System.out.println(optionalCart);
	    if (optionalCart.isPresent()) {
	        Cart cart = optionalCart.get();
	        System.out.println(cart+"cart to update from failed method ");
	        cart.getItems().removeIf(item -> item.getProductId().equals(event));
	        cartRepository.save(cart);
	    }

	}
}

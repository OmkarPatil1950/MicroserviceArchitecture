package com.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cart.entity.Cart;
import com.cart.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping("/{userId}")
	public ResponseEntity<Cart> getCart(@PathVariable String userId) {
		Cart cart = cartService.getCartByUserId(userId);
		return ResponseEntity.ok(cart);
	}

	@PostMapping("/{userId}/add")
	@PreAuthorize("hasRole('user')")
	public ResponseEntity<Cart> addItemToCart(@PathVariable String userId, @RequestParam Long productId,
			@RequestParam Integer quantity) {
		Cart cart = cartService.addItemToCart(userId, productId, quantity);
		return ResponseEntity.ok(cart);
	}

	@DeleteMapping("/{userId}/remove/{itemId}")
	public ResponseEntity<Void> removeItemFromCart(@PathVariable String userId, @PathVariable Long itemId) {
		cartService.removeItemFromCart(userId, itemId);
		return ResponseEntity.noContent().build();
	}
}

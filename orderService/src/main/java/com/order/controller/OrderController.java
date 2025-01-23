package com.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import com.order.entity.Order;
import com.order.entity.OrderItem;
import com.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/{userId}/create")
	public ResponseEntity<Order> createOrder(@PathVariable Long userId, @RequestBody List<OrderItem> items) {
		Order order = orderService.createOrder(userId, items);
		return ResponseEntity.ok(order);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
		List<Order> orders = orderService.getOrdersByUserId(userId);
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/details/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
		Order order = orderService.getOrderById(orderId);
		return ResponseEntity.ok(order);
	}

	
}

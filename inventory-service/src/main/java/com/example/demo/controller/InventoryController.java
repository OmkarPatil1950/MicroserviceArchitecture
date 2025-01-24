package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.dto.CartEvent;
import com.example.demo.entity.Inventory;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

	@Autowired
    private final InventoryService inventoryService;
    
    @Autowired
    private KafkaTemplate<String, Long>kafkaTemplate;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.addInventory(inventory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(
            @PathVariable Long id,
            @RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, inventory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('user')")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @GetMapping("/sku/{skuCode}")
//	@PreAuthorize("hasRole('user')")
    public ResponseEntity<Inventory> getInventoryBySkuCode(@PathVariable String skuCode) {
        return ResponseEntity.ok(inventoryService.getInventoryBySkuCode(skuCode));
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/in-stock/{ProductId}")
    public ResponseEntity<Boolean> isProductInStock(@PathVariable Long ProductId) {
        return ResponseEntity.ok(inventoryService.isProductInStock(ProductId));
    }
    
    
    
    @KafkaListener(topics = "cartTopic", groupId = "InventoryGruop")
    public void kafkaListner(@Autowired CartEvent cartEvent) {
    	System.out.println(cartEvent);
    	Long prouctId = cartEvent.getProductId();
    	Boolean isInStock= inventoryService.isProductInStock(prouctId);
    	if(isInStock) {
            System.out.println("Inventory available for product ID: " + cartEvent.getProductId());
    	}
    	else {
    		System.out.println("It is not in stock");
    		kafkaTemplate.send("inventoryFailureTopic",cartEvent.getProductId());
    	}
    }
}


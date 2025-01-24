package com.example.demo.serviceImplementation;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Inventory;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.service.InventoryService;


@Service
public class InventoryServiceImplementation implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImplementation(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    
    @Override
    @Cacheable(value = "inventory", key = "#id")
    public Inventory getInventoryById(Long id) {
    	simulateDelay(); // Simulate delay
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found!"));
    }

    @Override
    @Cacheable(value = "inventoryBySku", key = "#skuCode")
    public Inventory getInventoryBySkuCode(String skuCode) {
    	simulateDelay(); // Simulate delay
        return inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found!"));
    }

    @Override
    @CacheEvict(value = "inventory", key = "#id")
    public void deleteInventory(Long id) {
    	simulateDelay(); // Simulate delay
        inventoryRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = {"inventory", "inventoryBySku"}, allEntries = true)
    public Inventory updateInventory(Long id, Inventory inventory) {
    	simulateDelay(); // Simulate delay
        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found!"));
        existingInventory.setProductName(inventory.getProductName());
        existingInventory.setQuantity(inventory.getQuantity());
        existingInventory.setPrice(inventory.getPrice());
        return inventoryRepository.save(existingInventory);
    }
    

    @Override
    public Inventory addInventory(Inventory inventory) {
        if (inventoryRepository.existsBySkuCode(inventory.getSkuCode())) {
            throw new IllegalArgumentException("SKU Code already exists!");
        }
        return inventoryRepository.save(inventory);
    }
    
    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @Override
    public boolean isProductInStock(Long productId) {
        Inventory inventory = inventoryRepository.findById(productId).orElse(null);
        if (inventory == null) {
            return false; // Product is not found in inventory
        }
        return inventory.getQuantity() > 0;
    }

 // Simulate delay for database call
    private void simulateDelay() {
        try {
            Thread.sleep(3000); // 3-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted during delay simulation", e);
        }
    }

	
}
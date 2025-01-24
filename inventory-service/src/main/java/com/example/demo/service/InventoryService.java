package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Inventory;

public interface InventoryService {

    Inventory addInventory(Inventory inventory);

    Inventory updateInventory(Long id, Inventory inventory);

    void deleteInventory(Long id);

    Inventory getInventoryById(Long id);

    Inventory getInventoryBySkuCode(String skuCode);

    List<Inventory> getAllInventory();

    boolean isProductInStock(Long ProductId);
}

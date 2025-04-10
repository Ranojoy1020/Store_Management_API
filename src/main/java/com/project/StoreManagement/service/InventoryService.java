package com.project.StoreManagement.service;

import com.project.StoreManagement.entity.Inventory;
import com.project.StoreManagement.entity.Product;
import com.project.StoreManagement.exception.ProductNotFoundException;
import com.project.StoreManagement.repository.InventoryRepository;
import com.project.StoreManagement.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository, NotificationService notificationService) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.notificationService = notificationService;
    }

    // Get all inventory items
    public java.util.List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    // Get inventory for a specific product
    public Optional<Inventory> getInventoryByProduct(Long productId) {
        return inventoryRepository.findByProduct_ProductId(productId);
    }

    // Add or update inventory for a product
    public Inventory updateInventory(Long productId, int stockQuantity, int minStockThreshold) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + "not found"));

        // Check if the product is in inventory or not.
        // If it exists, us it, otherwise add product to inventory (make new inventory)
        Optional<Inventory> existingInventory = inventoryRepository.findByProduct_ProductId(productId);
        Inventory inventory = existingInventory.orElse(new Inventory());

        inventory.setProduct(product);
        inventory.setStockQuantity(stockQuantity);
        inventory.setMinStockThreshold(minStockThreshold);

        // Check if stock is low
        if (stockQuantity < minStockThreshold) {
            notificationService.sendLowStockAlert(product.getName(), stockQuantity);
        }

        return inventoryRepository.save(inventory);
    }

    // Delete inventory entry
    public void deleteInventory(Long inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }
}


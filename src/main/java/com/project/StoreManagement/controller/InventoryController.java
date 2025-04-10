package com.project.StoreManagement.controller;

import com.project.StoreManagement.entity.Inventory;
import com.project.StoreManagement.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/allInventory")
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{productId}")
    public Optional<Inventory> getInventoryByProduct(@PathVariable Long productId) {
        return inventoryService.getInventoryByProduct(productId);
    }

    @PostMapping("/{productId}")
    public Inventory updateInventory(
            @PathVariable Long productId,
            @RequestParam int stockQuantity,
            @RequestParam int minStockThreshold) {
        return inventoryService.updateInventory(productId, stockQuantity, minStockThreshold);
    }

    @DeleteMapping("/{inventoryId}")
    public void deleteInventory(@PathVariable Long inventoryId) {
        inventoryService.deleteInventory(inventoryId);
    }
}

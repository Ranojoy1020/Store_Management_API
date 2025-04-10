package com.project.StoreManagement.service;

import com.project.StoreManagement.entity.Sales;
import com.project.StoreManagement.entity.SalesItem;
import com.project.StoreManagement.entity.Inventory;
import com.project.StoreManagement.repository.SalesRepository;
import com.project.StoreManagement.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class SalesService {
    private final SalesRepository salesRepository;
    private final InventoryRepository inventoryRepository;

    public SalesService(SalesRepository salesRepository, InventoryRepository inventoryRepository) {
        this.salesRepository = salesRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public Sales recordSale(Sales sale) {
        sale.setSaleDate(LocalDate.now()); // Auto-set sale date

        double totalAmount = 0;
        for (SalesItem item : sale.getSalesItems()) {
            // Validate stock before processing sale
            Inventory inventory = inventoryRepository.findByProduct_ProductId(item.getProduct().getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found in inventory"));

            if (inventory.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + item.getProduct().getName());
            }

            // Deduct stock from inventory
            inventory.setStockQuantity(inventory.getStockQuantity() - item.getQuantity());
            inventoryRepository.save(inventory);

            // Set the sale reference for SalesItem
            item.setSale(sale);

            // Calculate total price per item
            totalAmount += item.getTotalItemPrice();
        }

        // Set total sale amount
        sale.setTotalAmount(totalAmount);

        return salesRepository.save(sale);
    }

    public List<Sales> getAllSales() {
        return salesRepository.findAll();
    }
}

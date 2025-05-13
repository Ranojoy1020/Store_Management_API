package com.project.StoreManagement.repository;

import com.project.StoreManagement.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct_ProductId(Long productId);

    @Query("SELECT i FROM Inventory i WHERE i.stockQuantity < i.minStockThreshold")
    List<Inventory> findLowStockItems();
}


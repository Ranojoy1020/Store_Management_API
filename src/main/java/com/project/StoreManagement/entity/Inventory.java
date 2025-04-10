package com.project.StoreManagement.entity;

import com.project.StoreManagement.enums.Unit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId", nullable = false)
    private Product product;

    private int stockQuantity;  // Total stock available
    private int minStockThreshold; // For low-stock alerts

    private double quantityPerUnit; // Example: 1kg, 500ml
    private double costPrice; // Purchase price per unit

    @Enumerated(EnumType.STRING)
    private Unit unit; // Enum for unit of measurement (KG, LITER, PIECE, etc.)
}

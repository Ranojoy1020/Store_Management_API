package com.project.StoreManagement.entity;

import com.project.StoreManagement.enums.Unit;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "barcode", unique = true, nullable = true)
    private String barcode;

    private String name;
    private String category;
    private double price;
    private double quantityPerUnit;

    @Enumerated(EnumType.STRING)
    private Unit unit;
}


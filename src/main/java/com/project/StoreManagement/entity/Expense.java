package com.project.StoreManagement.entity;

import com.project.StoreManagement.enums.ExpenseCategory;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    private LocalDateTime expenseDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    @Column(nullable = true)
    private Long supplierId;

    private double amount;
    private String description;
}

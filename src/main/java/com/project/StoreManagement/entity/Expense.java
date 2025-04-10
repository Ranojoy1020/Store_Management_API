package com.project.StoreManagement.entity;

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
    private String category;
    private double amount;
    private String description;
}

package com.project.StoreManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.StoreManagement.enums.UdhaarStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Udhaar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long udhaarId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "sale_id")
    @JsonIgnore
    private Sales sale; // Only linked if the sale was on Udhaar

    private double amountDue;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private UdhaarStatus status; // "PENDING", "OVERDUE", "PAID"
}


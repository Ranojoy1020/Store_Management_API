package com.project.StoreManagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private double totalAmount;

    @Column(nullable = false)
    private LocalDate saleDate = LocalDate.now(); // Auto-set sale date

    @Column(nullable = false)
    private String paymentMode; // Example: "CASH", "UPI", "CARD", "UDHAAR"

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SalesItem> salesItems;

    @OneToOne(mappedBy = "sales", cascade = CascadeType.ALL, optional = true)
    @JsonManagedReference
    private Udhaar udhaar; // Only applicable for "UDHAAR" payment mode

    public void setSalesItems(List<SalesItem> salesItems) {
        this.salesItems = salesItems;
        double total = 0;
        for (SalesItem item : salesItems) {
            item.setSale(this); // Ensure bidirectional link
            total += item.getTotalItemPrice();
        }
        this.totalAmount = total; // Auto-calculate total amount
    }
}

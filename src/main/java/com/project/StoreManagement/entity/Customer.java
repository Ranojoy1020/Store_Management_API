package com.project.StoreManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String fName;
    private String lName;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    private double totalUdhaarAmount;  // Tracks total pending Udhaar
    private int overdueCount;          // Tracks number of overdue Udhaar

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Udhaar> udhaarList;
}

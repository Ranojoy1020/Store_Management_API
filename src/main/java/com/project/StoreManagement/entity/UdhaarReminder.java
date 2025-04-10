package com.project.StoreManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UdhaarReminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reminderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "udhaar_id", nullable = false)
    private Udhaar udhaar;

    private LocalDate reminderDate;
    private String status; // SENT, FAILED
}

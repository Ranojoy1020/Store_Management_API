package com.project.StoreManagement.dto;

import com.project.StoreManagement.entity.Udhaar;
import com.project.StoreManagement.enums.UdhaarStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UdhaarDTO {
    private Long udhaarId;
    private Long customerId;
    private String customerName; // You might want to include customer name
    private Long saleId;
    private double amountDue;
    private LocalDate dueDate;
    private UdhaarStatus status;

    public static UdhaarDTO fromEntity(Udhaar udhaar) {
        UdhaarDTO dto = new UdhaarDTO();
        dto.setUdhaarId(udhaar.getUdhaarId());
        dto.setCustomerId(udhaar.getCustomer().getCustomerId());
        dto.setCustomerName(udhaar.getCustomer().getFName() + " " + udhaar.getCustomer().getLName()); // Assuming Customer has a getName()
        dto.setSaleId(udhaar.getSale().getSaleId());
        dto.setAmountDue(udhaar.getAmountDue());
        dto.setDueDate(udhaar.getDueDate());
        dto.setStatus(udhaar.getStatus());
        return dto;
    }
}
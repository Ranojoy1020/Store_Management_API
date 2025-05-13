package com.project.StoreManagement.dto;

import com.project.StoreManagement.entity.Sales;
import com.project.StoreManagement.enums.UdhaarStatus;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SalesDTO {
    private Long saleId;
    private Long customerId;
    private String customerName; // You might want to include customer name
    private double totalAmount;
    private LocalDateTime saleDate;
    private String paymentMode;
    private List<SalesItemDTO> salesItems;
    private Long udhaarId; // Just include the ID of the associated Udhaar

    public static SalesDTO fromEntity(Sales sales) {
        SalesDTO dto = new SalesDTO();
        dto.setSaleId(sales.getSaleId());
        dto.setCustomerId(sales.getCustomer().getCustomerId());
        dto.setCustomerName(sales.getCustomer().getFName() + " " + sales.getCustomer().getLName()); // Assuming Customer has a getName()
        dto.setTotalAmount(sales.getTotalAmount());
        dto.setSaleDate(sales.getSaleDate());
        dto.setPaymentMode(sales.getPaymentMode());
        dto.setSalesItems(sales.getSalesItems().stream().map(SalesItemDTO::fromEntity).collect(Collectors.toList()));
        if (sales.getUdhaar() != null) {
            dto.setUdhaarId(sales.getUdhaar().getUdhaarId());
        }
        return dto;
    }
}
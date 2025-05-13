package com.project.StoreManagement.dto;

import com.project.StoreManagement.entity.SalesItem;
import lombok.Data;

@Data
public class SalesItemDTO {
    private Long salesItemId;
    private Long productId;
    private String productName; // Assuming Product has a getName()
    private int quantity;
    private double unitPrice;
    private double totalItemPrice;

    public static SalesItemDTO fromEntity(SalesItem salesItem) {
        SalesItemDTO dto = new SalesItemDTO();
        dto.setSalesItemId(salesItem.getSaleItemId());
        dto.setProductId(salesItem.getProduct().getProductId()); // Assuming SalesItem has a Product
        dto.setProductName(salesItem.getProduct().getName()); // Assuming Product has a getName()
        dto.setQuantity(salesItem.getQuantity());
        dto.setUnitPrice(salesItem.getUnitPrice());
        dto.setTotalItemPrice(salesItem.getTotalItemPrice());
        return dto;
    }
}
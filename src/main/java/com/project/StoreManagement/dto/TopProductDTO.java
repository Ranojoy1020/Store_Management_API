package com.project.StoreManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopProductDTO {
    private String productName;
    private Long quantitySold;
}


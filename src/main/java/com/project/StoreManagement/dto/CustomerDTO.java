package com.project.StoreManagement.dto;

import com.project.StoreManagement.entity.Customer;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {
    private Long customerId;
    private String fName;
    private String lName;
    private String phone;
    private String email;

    private double totalUdhaarAmount;
    private int overdueCount;

    // Optional: Add if you want to show udhaar IDs without full object tree
     private List<Long> udhaarIds;

    public static CustomerDTO fromEntity(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setFName(customer.getFName());
        dto.setLName(customer.getLName());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        //  The following would be calculated in the service layer, not directly from the entity.
        dto.setTotalUdhaarAmount(0.0);
        dto.setOverdueCount(0);
        dto.setUdhaarIds(null); // Initialize as null. Will be set in service.
        return dto;
    }
}

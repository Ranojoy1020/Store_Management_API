package com.project.StoreManagement.dto;

import lombok.Data;

@Data
public class PasswordUpdateRequest {
    private String username;
    private String oldPassword;
    private String newPassword;
}

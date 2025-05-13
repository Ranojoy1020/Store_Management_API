package com.project.StoreManagement.service;

import com.project.StoreManagement.entity.Admin;
import com.project.StoreManagement.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(Admin user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        adminRepository.save(user);
    }

    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username).orElse(null);
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email).orElse(null);
    }

    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        Admin admin = this.findByUsername(username);
        if (admin == null) return false;

        if (!passwordEncoder.matches(oldPassword, admin.getPassword())) {
            return false; // Incorrect current password
        }

        admin.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(admin);
        return true;
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}



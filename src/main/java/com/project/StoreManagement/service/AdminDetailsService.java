package com.project.StoreManagement.service;

import com.project.StoreManagement.entity.Admin;
import com.project.StoreManagement.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        return new org.springframework.security.core.userdetails.User(
                admin.getUsername(),
                admin.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }
}

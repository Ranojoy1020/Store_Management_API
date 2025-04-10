package com.project.StoreManagement.repository;

import com.project.StoreManagement.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Fetch all customers who have at least one overdue Udhaar
    List<Customer> findByOverdueCountGreaterThan(int count);
}
package com.project.StoreManagement.repository;

import com.project.StoreManagement.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UdhaarRepository extends JpaRepository<Udhaar, Long> {
    List<Udhaar> findByDueDateBeforeAndStatus(LocalDate date, String status);

    List<Udhaar> findByCustomer_CustomerId(Long customerId);

    List<Udhaar> findByStatus(String overdue);
}
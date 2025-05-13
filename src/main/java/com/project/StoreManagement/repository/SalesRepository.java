package com.project.StoreManagement.repository;

import com.project.StoreManagement.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {

    List<Sales> findAllByOrderBySaleDateDesc();

    @Query("""
            SELECT s FROM Sales s
            WHERE (s.saleDate >= :startDate)
            AND (s.saleDate <= :endDate)
            AND(:customerId IS NULL OR s.customer.customerId = :customerId)
            AND(:paymentMode IS NULL OR s.paymentMode = :paymentMode)
            """)
    List<Sales> getReport(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("customerId") Long customerId,
            @Param("paymentMode") String paymentMode
    );
}
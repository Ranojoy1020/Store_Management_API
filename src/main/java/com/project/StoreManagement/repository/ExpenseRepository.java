package com.project.StoreManagement.repository;

import com.project.StoreManagement.entity.*;
import com.project.StoreManagement.enums.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByExpenseDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("""
            SELECT e FROM Expense e
            WHERE (e.expenseDate >= :startDate)
            AND (e.expenseDate <= :endDate)
            AND(:category IS NULL OR e.category = :category)
            """)
    List<Expense> getReport(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("category") ExpenseCategory category
    );
}

package com.project.StoreManagement.service;

import com.project.StoreManagement.entity.Expense;
import com.project.StoreManagement.entity.Sales;
import com.project.StoreManagement.enums.ExpenseCategory;
import com.project.StoreManagement.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpenseBetweenDates(LocalDate start, LocalDate end){
        return expenseRepository.findByExpenseDateBetween(start, end);
    }

    public List<Expense> getExpenseReport(LocalDate start, LocalDate end, ExpenseCategory category) {
        return expenseRepository.getReport(start.atStartOfDay(), end.atTime(LocalTime.MAX), category);
    }
}

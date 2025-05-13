package com.project.StoreManagement.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.project.StoreManagement.entity.Expense;
import com.project.StoreManagement.entity.Sales;
import com.project.StoreManagement.enums.ExpenseCategory;
import com.project.StoreManagement.service.ExpenseService;
import com.project.StoreManagement.service.SalesService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private SalesService salesService;
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/sales")
    public void generateSalesReport(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam("customerId") @Nullable Long customerId,
            @RequestParam("paymentMode") @Nullable String paymentMode,
            HttpServletResponse response
    ) throws IOException, DocumentException {

        List<Sales> sales = salesService.getSalesReport(startDate, endDate,customerId, paymentMode);

        if (sales.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NO_CONTENT, "No sales data found.");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=sales_report.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Paragraph companyHeader = new Paragraph("Platinum Market");
        companyHeader.setAlignment(Element.ALIGN_CENTER);
        document.add(companyHeader);
        document.add(new Paragraph("Sales Report"));
        document.add(new Paragraph("From: " + startDate + " To: " + endDate));
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(5); // Adjust columns as needed
        table.setWidthPercentage(100);
        table.addCell("Sale ID");
        table.addCell("Date");
        table.addCell("Customer");
        table.addCell("Amount");
        table.addCell("Payment Mode");

        for (Sales sale : sales) {
            table.addCell(String.valueOf(sale.getSaleId()));
            table.addCell(String.valueOf(sale.getSaleDate()));
            table.addCell(sale.getCustomer().getFName() + " " + sale.getCustomer().getLName());
            table.addCell("₹" + sale.getTotalAmount());
            table.addCell(sale.getPaymentMode());
        }

        document.add(table);
        document.close();
    }

    @GetMapping("/expenses")
    public void generateExpenseReport(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam("category") @Nullable ExpenseCategory category,
            HttpServletResponse response
    ) throws IOException, DocumentException{

        List<Expense> expenses = expenseService.getExpenseReport(startDate, endDate, category);

        if (expenses.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NO_CONTENT, "No expense data found.");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=expense_report.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Paragraph companyHeader = new Paragraph("Platinum Market");
        companyHeader.setAlignment(Element.ALIGN_CENTER);
        document.add(companyHeader);
        document.add(new Paragraph("Expense Report"));
        document.add(new Paragraph("From: " + startDate + " To: " + endDate));
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(5); // Adjust columns as needed
        table.setWidthPercentage(100);
        table.addCell("Expense ID");
        table.addCell("Date");
        table.addCell("Category");
        table.addCell("Amount");
        table.addCell("Description");

        for (Expense expense : expenses) {
            table.addCell(String.valueOf(expense.getExpenseId()));
            table.addCell(String.valueOf(expense.getExpenseDate()));
            table.addCell(String.valueOf(expense.getCategory()));
            table.addCell("₹" + expense.getAmount());
            table.addCell(expense.getDescription());
        }

        document.add(table);
        document.close();
    }
}

package com.project.StoreManagement.service;

import com.project.StoreManagement.dto.SalesDTO;
import com.project.StoreManagement.dto.TopProductDTO;
import com.project.StoreManagement.entity.*;
import com.project.StoreManagement.enums.UdhaarStatus;
import com.project.StoreManagement.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesService {
    private final SalesRepository salesRepository;
    private final SalesItemRepository salesItemRepository;
    private final InventoryRepository inventoryRepository;
    private final UdhaarService udhaarService;

    public SalesService(SalesRepository salesRepository,SalesItemRepository salesItemRepository, InventoryRepository inventoryRepository, UdhaarService udhaarService) {
        this.salesRepository = salesRepository;
        this.salesItemRepository = salesItemRepository;
        this.inventoryRepository = inventoryRepository;
        this.udhaarService = udhaarService;
    }

    public Sales recordSale(Sales sale) {
        sale.setSaleDate(LocalDateTime.now()); // Auto-set sale date

        double totalAmount = 0;
        for (SalesItem item : sale.getSalesItems()) {
            // Validate stock before processing sale
            Inventory inventory = inventoryRepository.findByProduct_ProductId(item.getProduct().getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found in inventory"));

            if (inventory.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + item.getProduct().getName());
            }

            // Deduct stock from inventory
            inventory.setStockQuantity(inventory.getStockQuantity() - item.getQuantity());
            inventoryRepository.save(inventory);

            // Set the sale reference for SalesItem
            item.setSale(sale);

            // Calculate total price per item
            totalAmount += item.getTotalItemPrice();
        }

        // Set total sale amount
        sale.setTotalAmount(totalAmount);
        Sales savedSale = salesRepository.save(sale);

        // 🧾 Automatically create Udhaar if payment mode is UDHAAR
        if ("UDHAAR".equalsIgnoreCase(savedSale.getPaymentMode())) {

            if (savedSale.getUdhaar() == null) {
                savedSale.setUdhaar(udhaarService.createUdhaarForSale(savedSale.getSaleId()));
                return salesRepository.save(savedSale);
            }
        }


        return savedSale;
    }

    public List<SalesDTO> getAllSales() {
        List<Sales> salesList = salesRepository.findAll();
        return salesList.stream().map(SalesDTO::fromEntity).collect(Collectors.toList());
    }

    public List<SalesDTO> getAllSalesDesc() {
        List<Sales> salesList = salesRepository.findAll(Sort.by(Sort.Order.desc("saleDate")));
        return salesList.stream().map(SalesDTO::fromEntity).collect(Collectors.toList());
    }

    public List<TopProductDTO> getTopSellingProducts(String period) {
        LocalDateTime startDateTime = null;

        if ("week".equalsIgnoreCase(period)) {
            startDateTime = LocalDateTime.now(Clock.systemDefaultZone()).minusDays(7);
        } else if ("month".equalsIgnoreCase(period)) {
            startDateTime = LocalDateTime.now(Clock.systemDefaultZone()).minusMonths(1);
        }

        List<Object[]> results = salesItemRepository.findTopSellingProductsWithDateFilter(startDateTime);

        return results.stream()
                .map(row -> new TopProductDTO((String) row[0], ((Number) row[1]).longValue()))
                .collect(Collectors.toList());
    }


    public List<Sales> getSalesReport(LocalDate start, LocalDate end, Long customerId, String paymentMode) {
        return salesRepository.getReport(start.atStartOfDay(), end.atTime(LocalTime.MAX),customerId, paymentMode);
    }
}

package com.project.StoreManagement.controller;

import com.project.StoreManagement.entity.Sales;
import com.project.StoreManagement.service.SalesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesController {
    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @PostMapping("/recordSale")
    public Sales recordSale(@RequestBody Sales sale) {
        return salesService.recordSale(sale);
    }

    @GetMapping("/allSales")
    public List<Sales> getAllSales() {
        return salesService.getAllSales();
    }
}

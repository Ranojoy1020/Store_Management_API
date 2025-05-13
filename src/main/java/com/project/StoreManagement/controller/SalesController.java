package com.project.StoreManagement.controller;

import com.project.StoreManagement.dto.SalesDTO;
import com.project.StoreManagement.dto.TopProductDTO;
import com.project.StoreManagement.entity.Sales;
import com.project.StoreManagement.service.SalesService;
import org.springframework.http.ResponseEntity;
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
    public List<SalesDTO> getAllSales() {
        return salesService.getAllSales();
    }

    @GetMapping("/allSalesDesc")
    public List<SalesDTO> getAllSalesDesc() {
        return salesService.getAllSalesDesc();
    }

    @GetMapping("/top-products/{period}")
    public ResponseEntity<List<TopProductDTO>> getTopSellingProducts(@PathVariable String period) {
        List<TopProductDTO> topProducts = salesService.getTopSellingProducts(period);
        return ResponseEntity.ok(topProducts);
    }
}

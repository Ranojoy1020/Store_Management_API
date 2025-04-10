package com.project.StoreManagement.controller;

import com.project.StoreManagement.entity.SalesItem;
import com.project.StoreManagement.service.SalesItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales-items")
public class SalesItemController {
    private final SalesItemService salesItemService;

    public SalesItemController(SalesItemService salesItemService) {
        this.salesItemService = salesItemService;
    }

    @GetMapping
    public List<SalesItem> getAllSalesItems() {
        return salesItemService.getAllSalesItems();
    }

    @PostMapping
    public SalesItem addSalesItem(@RequestBody SalesItem salesItem) {
        return salesItemService.addSalesItem(salesItem);
    }

    @GetMapping("/sale/{saleId}")
    public List<SalesItem> getSalesItemsBySaleId(@PathVariable Long saleId) {
        return salesItemService.getSalesItemsBySaleId(saleId);
    }
}

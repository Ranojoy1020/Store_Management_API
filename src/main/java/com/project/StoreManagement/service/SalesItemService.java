package com.project.StoreManagement.service;

import com.project.StoreManagement.entity.SalesItem;
import com.project.StoreManagement.repository.SalesItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesItemService {
    private final SalesItemRepository salesItemRepository;

    public SalesItemService(SalesItemRepository salesItemRepository) {
        this.salesItemRepository = salesItemRepository;
    }

    public List<SalesItem> getAllSalesItems() {
        return salesItemRepository.findAll();
    }

    public SalesItem addSalesItem(SalesItem salesItem) {
        return salesItemRepository.save(salesItem);
    }

    public List<SalesItem> getSalesItemsBySaleId(Long saleId) {
        return salesItemRepository.findBySale_SaleId(saleId);
    }
}

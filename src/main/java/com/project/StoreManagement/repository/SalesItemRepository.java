package com.project.StoreManagement.repository;

import com.project.StoreManagement.dto.TopProductDTO;
import com.project.StoreManagement.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface SalesItemRepository extends JpaRepository<SalesItem, Long> {
    List<SalesItem> findBySale_SaleId(Long saleId);

    @Query(
            value = "SELECT p.name AS productName, SUM(si.quantity) AS quantitySold " +
                    "FROM sales_item si " +
                    "JOIN product p ON si.product_id = p.product_id " +
                    "GROUP BY p.name " +
                    "ORDER BY quantitySold DESC" +
                    "LIMIT 5",
            nativeQuery = true
    )
    List<Object[]> findTopSellingProductsNative();


    @Query( """
      SELECT p.name AS productName, SUM(si.quantity) AS quantitySold
      FROM SalesItem si
      JOIN Product p ON si.product.productId = p.productId
      GROUP BY p.name
      ORDER BY quantitySold DESC
      LIMIT 5
      """
    )
    List<Object[]> findTopSellingProductsWithDateFilter(@Param("startDate") LocalDateTime startDate);

}

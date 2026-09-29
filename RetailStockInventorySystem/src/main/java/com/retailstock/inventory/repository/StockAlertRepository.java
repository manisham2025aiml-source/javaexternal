package com.retailstock.inventory.repository;

import com.retailstock.inventory.entity.Product;
import com.retailstock.inventory.entity.StockAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockAlertRepository extends JpaRepository<StockAlert, Long> {
    List<StockAlert> findByResolvedFalseOrderByCreatedAtDesc();
    boolean existsByProductAndResolvedFalse(Product product);
    List<StockAlert> findByProductAndResolvedFalse(Product product);
}

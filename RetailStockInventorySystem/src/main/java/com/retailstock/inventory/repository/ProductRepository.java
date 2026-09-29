package com.retailstock.inventory.repository;

import com.retailstock.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductCode(String productCode);
    boolean existsByProductCode(String productCode);
    List<Product> findByCurrentStockLessThanEqual(Integer level);
}

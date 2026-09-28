package com.retailstock.inventory.repository;

import com.retailstock.inventory.entity.MovementType;
import com.retailstock.inventory.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findAllByOrderByMovedAtDesc();

    @Query("select new com.retailstock.inventory.dto.FastMovingProduct(p.id, p.productCode, p.name, sum(m.quantity)) " +
           "from StockMovement m join m.product p " +
           "where m.movementType = :type and m.movedAt between :from and :to " +
           "group by p.id, p.productCode, p.name order by sum(m.quantity) desc")
    List<com.retailstock.inventory.dto.FastMovingProduct> findFastMoving(
            @Param("type") MovementType type,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);
}

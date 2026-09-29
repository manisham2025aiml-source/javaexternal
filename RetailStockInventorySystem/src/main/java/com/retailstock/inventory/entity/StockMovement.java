package com.retailstock.inventory.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MovementType movementType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer stockBefore;

    @Column(nullable = false)
    private Integer stockAfter;

    @Column(nullable = false)
    private LocalDateTime movedAt;

    public StockMovement() {}

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public MovementType getMovementType() { return movementType; }
    public void setMovementType(MovementType movementType) { this.movementType = movementType; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Integer getStockBefore() { return stockBefore; }
    public void setStockBefore(Integer stockBefore) { this.stockBefore = stockBefore; }
    public Integer getStockAfter() { return stockAfter; }
    public void setStockAfter(Integer stockAfter) { this.stockAfter = stockAfter; }
    public LocalDateTime getMovedAt() { return movedAt; }
    public void setMovedAt(LocalDateTime movedAt) { this.movedAt = movedAt; }
}

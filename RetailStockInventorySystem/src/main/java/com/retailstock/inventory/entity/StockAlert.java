package com.retailstock.inventory.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_alerts")
public class StockAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer stockAtAlert;

    @Column(nullable = false)
    private Integer reorderLevel;

    @Column(nullable = false)
    private Boolean resolved = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public StockAlert() {}

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getStockAtAlert() { return stockAtAlert; }
    public void setStockAtAlert(Integer stockAtAlert) { this.stockAtAlert = stockAtAlert; }
    public Integer getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }
    public Boolean getResolved() { return resolved; }
    public void setResolved(Boolean resolved) { this.resolved = resolved; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

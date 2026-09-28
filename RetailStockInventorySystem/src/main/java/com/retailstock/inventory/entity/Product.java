package com.retailstock.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 30)
    private String productCode;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String name;

    @DecimalMin(value = "0.0")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Min(0)
    @Column(nullable = false)
    private Integer currentStock = 0;

    @Min(0)
    @Column(nullable = false)
    private Integer reorderLevel = 5;

    public Product() {}

    public Long getId() { return id; }
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getCurrentStock() { return currentStock; }
    public void setCurrentStock(Integer currentStock) { this.currentStock = currentStock; }
    public Integer getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }
}

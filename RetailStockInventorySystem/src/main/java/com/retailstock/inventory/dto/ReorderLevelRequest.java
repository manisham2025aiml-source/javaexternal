package com.retailstock.inventory.dto;

import jakarta.validation.constraints.Min;

public class ReorderLevelRequest {
    @Min(value = 0, message = "Reorder level cannot be negative")
    private Integer reorderLevel;

    public Integer getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }
}

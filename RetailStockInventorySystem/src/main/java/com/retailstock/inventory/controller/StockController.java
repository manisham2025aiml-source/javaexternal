package com.retailstock.inventory.controller;

import com.retailstock.inventory.dto.ReorderLevelRequest;
import com.retailstock.inventory.dto.StockRequest;
import com.retailstock.inventory.entity.Product;
import com.retailstock.inventory.entity.StockMovement;
import com.retailstock.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    private final InventoryService service;
    public StockController(InventoryService service) { this.service = service; }

    @PostMapping("/{id}/in")
    public Product stockIn(@PathVariable Long id, @Valid @RequestBody StockRequest request) {
        return service.stockIn(id, request);
    }

    @PostMapping("/{id}/out")
    public Product stockOut(@PathVariable Long id, @Valid @RequestBody StockRequest request) {
        return service.stockOut(id, request);
    }

    @GetMapping("/movements")
    public List<StockMovement> movements() { return service.getMovementHistory(); }

    @PutMapping("/{id}/reorder-level")
    public Product reorderLevel(@PathVariable Long id, @Valid @RequestBody ReorderLevelRequest request) {
        return service.setReorderLevel(id, request);
    }
}

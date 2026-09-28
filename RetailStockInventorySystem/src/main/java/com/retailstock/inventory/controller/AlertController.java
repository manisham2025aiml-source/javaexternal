package com.retailstock.inventory.controller;

import com.retailstock.inventory.entity.StockAlert;
import com.retailstock.inventory.service.InventoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    private final InventoryService service;
    public AlertController(InventoryService service) { this.service = service; }

    @GetMapping
    public List<StockAlert> openAlerts() { return service.getOpenAlerts(); }

    @PutMapping("/{id}/resolve")
    public StockAlert resolve(@PathVariable Long id) { return service.resolveAlert(id); }
}

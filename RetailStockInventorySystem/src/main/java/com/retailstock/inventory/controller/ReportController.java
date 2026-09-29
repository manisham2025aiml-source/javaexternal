package com.retailstock.inventory.controller;

import com.retailstock.inventory.dto.FastMovingProduct;
import com.retailstock.inventory.service.InventoryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final InventoryService service;
    public ReportController(InventoryService service) { this.service = service; }

    @GetMapping("/fast-moving")
    public List<FastMovingProduct> fastMoving(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (from.isAfter(to)) throw new IllegalArgumentException("from date cannot be after to date");
        return service.fastMovingProducts(from, to);
    }
}

package com.retailstock.inventory.controller;

import com.retailstock.inventory.entity.Product;
import com.retailstock.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final InventoryService service;
    public ProductController(InventoryService service) { this.service = service; }

    @GetMapping
    public List<Product> all() { return service.getAllProducts(); }

    @GetMapping("/{id}")
    public Product one(@PathVariable Long id) { return service.getProduct(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product add(@Valid @RequestBody Product product) { return service.addProduct(product); }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @Valid @RequestBody Product product) {
        return service.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteProduct(id); }
}

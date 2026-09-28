package com.retailstock.inventory.service;

import com.retailstock.inventory.dto.FastMovingProduct;
import com.retailstock.inventory.dto.ReorderLevelRequest;
import com.retailstock.inventory.dto.StockRequest;
import com.retailstock.inventory.entity.*;
import com.retailstock.inventory.exception.DuplicateProductException;
import com.retailstock.inventory.exception.InsufficientStockException;
import com.retailstock.inventory.exception.ResourceNotFoundException;
import com.retailstock.inventory.repository.ProductRepository;
import com.retailstock.inventory.repository.StockAlertRepository;
import com.retailstock.inventory.repository.StockMovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class InventoryService {
    private final ProductRepository productRepository;
    private final StockMovementRepository movementRepository;
    private final StockAlertRepository alertRepository;

    public InventoryService(ProductRepository productRepository,
                            StockMovementRepository movementRepository,
                            StockAlertRepository alertRepository) {
        this.productRepository = productRepository;
        this.movementRepository = movementRepository;
        this.alertRepository = alertRepository;
    }

    public List<Product> getAllProducts() { return productRepository.findAll(); }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    @Transactional
    public Product addProduct(Product product) {
        if (productRepository.existsByProductCode(product.getProductCode())) {
            throw new DuplicateProductException("Product code already exists: " + product.getProductCode());
        }
        if (product.getCurrentStock() == null) product.setCurrentStock(0);
        if (product.getReorderLevel() == null) product.setReorderLevel(5);
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product input) {
        Product product = getProduct(id);
        product.setName(input.getName());
        product.setPrice(input.getPrice());
        product.setReorderLevel(input.getReorderLevel());
        checkAndCreateAlert(product);
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProduct(id);
        productRepository.delete(product);
    }

    @Transactional
    public Product stockIn(Long id, StockRequest request) {
        Product product = getProduct(id);
        int before = product.getCurrentStock();
        int after = before + request.getQuantity();
        product.setCurrentStock(after);
        movementRepository.save(createMovement(product, MovementType.STOCK_IN, request.getQuantity(), before, after));
        resolveOpenAlerts(product);
        return productRepository.save(product);
    }

    @Transactional
    public Product stockOut(Long id, StockRequest request) {
        Product product = getProduct(id);
        int before = product.getCurrentStock();
        if (request.getQuantity() > before) {
            throw new InsufficientStockException("Insufficient stock. Available: " + before + ", requested: " + request.getQuantity());
        }
        int after = before - request.getQuantity();
        product.setCurrentStock(after);
        movementRepository.save(createMovement(product, MovementType.STOCK_OUT, request.getQuantity(), before, after));
        Product saved = productRepository.save(product);
        checkAndCreateAlert(saved);
        return saved;
    }

    public List<StockMovement> getMovementHistory() {
        return movementRepository.findAllByOrderByMovedAtDesc();
    }

    public List<StockAlert> getOpenAlerts() {
        return alertRepository.findByResolvedFalseOrderByCreatedAtDesc();
    }

    @Transactional
    public Product setReorderLevel(Long id, ReorderLevelRequest request) {
        Product product = getProduct(id);
        product.setReorderLevel(request.getReorderLevel());
        Product saved = productRepository.save(product);
        if (saved.getCurrentStock() <= saved.getReorderLevel()) checkAndCreateAlert(saved);
        else resolveOpenAlerts(saved);
        return saved;
    }

    @Transactional
    public StockAlert resolveAlert(Long alertId) {
        StockAlert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found: " + alertId));
        alert.setResolved(true);
        return alertRepository.save(alert);
    }

    public List<FastMovingProduct> fastMovingProducts(LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);
        return movementRepository.findFastMoving(MovementType.STOCK_OUT, start, end);
    }

    private StockMovement createMovement(Product product, MovementType type, int quantity, int before, int after) {
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setMovementType(type);
        movement.setQuantity(quantity);
        movement.setStockBefore(before);
        movement.setStockAfter(after);
        movement.setMovedAt(LocalDateTime.now());
        return movement;
    }

    private void checkAndCreateAlert(Product product) {
        if (product.getCurrentStock() <= product.getReorderLevel()
                && !alertRepository.existsByProductAndResolvedFalse(product)) {
            StockAlert alert = new StockAlert();
            alert.setProduct(product);
            alert.setStockAtAlert(product.getCurrentStock());
            alert.setReorderLevel(product.getReorderLevel());
            alert.setResolved(false);
            alert.setCreatedAt(LocalDateTime.now());
            alertRepository.save(alert);
        }
    }

    private void resolveOpenAlerts(Product product) {
        List<StockAlert> alerts = alertRepository.findByProductAndResolvedFalse(product);
        alerts.forEach(a -> a.setResolved(true));
        alertRepository.saveAll(alerts);
    }
}

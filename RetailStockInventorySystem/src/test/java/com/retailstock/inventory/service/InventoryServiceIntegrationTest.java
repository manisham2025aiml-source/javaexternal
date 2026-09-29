package com.retailstock.inventory.service;

import com.retailstock.inventory.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class InventoryServiceIntegrationTest {

    @Autowired
    private InventoryService inventoryService;

    @Test
    void shouldUpdateProductInfoAndGenerateLowStockAlerts() {
        Product product = new Product();
        product.setProductCode("P100");
        product.setName("Rice Bag");
        product.setPrice(new BigDecimal("125.50"));
        product.setCurrentStock(12);
        product.setReorderLevel(5);

        Product created = inventoryService.addProduct(product);

        Product updated = new Product();
        updated.setProductCode("P100-NEW");
        updated.setName("Rice Bag Premium");
        updated.setPrice(new BigDecimal("149.00"));
        updated.setCurrentStock(3);
        updated.setReorderLevel(4);

        Product result = inventoryService.updateProduct(created.getId(), updated);

        assertThat(result.getProductCode()).isEqualTo("P100-NEW");
        assertThat(result.getName()).isEqualTo("Rice Bag Premium");
        assertThat(result.getCurrentStock()).isEqualTo(3);
        assertThat(result.getReorderLevel()).isEqualTo(4);
        assertThat(inventoryService.getOpenAlerts()).isNotEmpty();
    }
}

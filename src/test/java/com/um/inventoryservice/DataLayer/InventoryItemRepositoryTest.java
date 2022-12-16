package com.um.inventoryservice.DataLayer;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class InventoryItemRepositoryTest {

    @Autowired
    InventoryItemRepository inventoryItemRepository;

    StockItemDTO stockItemDTO = buildStockItemDTO();

    @Test
    public void insertInventoryItem (){
        InventoryItem inventoryItem = buildInventoryItem();

        Publisher<InventoryItem> setup = inventoryItemRepository.deleteAll().thenMany(inventoryItemRepository.save(inventoryItem));
        StepVerifier
                .create(setup)
                .consumeNextWith(foundItem -> {
                    assertEquals(inventoryItem.getInventoryItemId(), foundItem.getInventoryItemId());
                    assertEquals(inventoryItem.getStockItemDTO(), foundItem.getStockItemDTO());
                    assertEquals(inventoryItem.getQuantityInStock(), foundItem.getQuantityInStock());
                })
                .verifyComplete();
    }

    private InventoryItem buildInventoryItem() {
        return InventoryItem.builder()
                .inventoryItemId("1176543")
                .stockItemDTO(buildStockItemDTO())
                .quantityInStock(265)
                .build();
    }

    private InventoryItemDTO buildInventoryItemDTO() {
        return InventoryItemDTO.builder()
                .inventoryItemId("1123456")
                .stockItemDTO(buildStockItemDTO())
                .quantityInStock(75)
                .build();
    }

    private StockItemDTO buildStockItemDTO() {
        return StockItemDTO.builder()
                .stockItemId("297445493")
                .description("DTO test plumbing item")
                .supplierId(2005)
                .salesQuantity(53)
                .price(25.99)
                .build();
    }

}
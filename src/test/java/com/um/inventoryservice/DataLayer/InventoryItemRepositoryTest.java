package com.um.inventoryservice.DataLayer;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class InventoryItemRepositoryTest {

    @Autowired
    InventoryItemRepository inventoryItemRepository;

    InventoryItem inventoryItem = buildInventoryItem();

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

    @Test
    void getInventoryItemById() {
        Publisher<InventoryItem> setup = inventoryItemRepository.deleteAll().thenMany(inventoryItemRepository.save(inventoryItem));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        Mono<InventoryItem> find = inventoryItemRepository.findInventoryItemByInventoryItemId(inventoryItem.getInventoryItemId());
        Publisher<InventoryItem> composite = Mono
                .from(setup)
                .then(find);

        StepVerifier
                .create(composite)
                .consumeNextWith(foundInvItem -> {
                    assertEquals(inventoryItem.getInventoryItemId(), foundInvItem.getInventoryItemId());
                    assertEquals(inventoryItem.getStockItemDTO().getStockItemId(), foundInvItem.getStockItemDTO().getStockItemId());
                    assertEquals(inventoryItem.getQuantityInStock(), foundInvItem.getQuantityInStock());
                })
                .verifyComplete();
    }

    @Test
    public void getAllInventoryItems() {
        InventoryItem invItem = buildInventoryItem();

        Publisher<InventoryItem> setup = inventoryItemRepository.deleteAll().thenMany(inventoryItemRepository.save(invItem));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        Flux<InventoryItem> find = inventoryItemRepository.findAll();
        Publisher<InventoryItem> composite = Mono
                .from(setup)
                .thenMany(find);

        StepVerifier
                .create(composite)
                .consumeNextWith(foundInvItem ->{
                    assertEquals(invItem.getInventoryItemId(), foundInvItem.getInventoryItemId());
                    assertEquals(invItem.getStockItemDTO().getStockItemId(), foundInvItem.getStockItemDTO().getStockItemId());
                    assertEquals(invItem.getQuantityInStock(), foundInvItem.getQuantityInStock());
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
                .supplierName(2005)
                .quantitySold(53)
                .sellingPrice(25.99)
                .build();
    }

}
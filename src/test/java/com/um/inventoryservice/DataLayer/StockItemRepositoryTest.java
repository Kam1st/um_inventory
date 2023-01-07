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
class StockItemRepositoryTest {

    @Autowired
    StockItemRepository stockItemRepository;

    StockItem stockItem = buildStockItem();

    String STOCK_ID = stockItem.getStockItemId();

    StockItemDTO stockItemDTO = buildStockItemDTO();


    @Test
    public void getAllStockItems() {
        StockItem stockItem = buildStockItem();

        Publisher<StockItem> setup = stockItemRepository.deleteAll().thenMany(stockItemRepository.save(stockItem));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        Flux<StockItem> find = stockItemRepository.findAll();
        Publisher<StockItem> composite = Mono
                .from(setup)
                .thenMany(find);

        StepVerifier.create(composite)
                .consumeNextWith(foundStockItem -> {
                    assertEquals(stockItem.getStockItemId(), foundStockItem.getStockItemId());
                    assertEquals(stockItem.getDescription(), foundStockItem.getDescription());
                    assertEquals(stockItem.getSupplierName(), foundStockItem.getSupplierName());
                    assertEquals(stockItem.getQuantitySold(), foundStockItem.getQuantitySold());
                    assertEquals(stockItem.getSellingPrice(), foundStockItem.getSellingPrice());
                    assertEquals(stockItem.getCostPrice(), foundStockItem.getCostPrice());
                })
                .verifyComplete();
    }

    @Test
    void getStockItemById() {
        Publisher<StockItem> setup = stockItemRepository.deleteAll().thenMany(stockItemRepository.save(stockItem));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        Mono<StockItem> find = stockItemRepository.findStockItemByStockItemId(stockItem.getStockItemId());
        Publisher<StockItem> composite = Mono
                .from(setup)
                .then(find);

        StepVerifier
                .create(composite)
                .consumeNextWith(foundStockItem -> {
                    assertEquals(stockItem.getStockItemId(), foundStockItem.getStockItemId());
                    assertEquals(stockItem.getDescription(), foundStockItem.getDescription());
                    assertEquals(stockItem.getSupplierName(), foundStockItem.getSupplierName());
                    assertEquals(stockItem.getQuantitySold(), foundStockItem.getQuantitySold());
                    assertEquals(stockItem.getSellingPrice(), foundStockItem.getSellingPrice());
                    assertEquals(stockItem.getCostPrice(), foundStockItem.getCostPrice());
                })
                .verifyComplete();
    }

    @Test
    public void findStockItemsBySellingPrice() {

        StockItem stockItem = buildStockItem();

        Publisher<StockItem> setup = stockItemRepository.deleteAll().thenMany(stockItemRepository.save(buildStockItem()));
        Publisher<StockItem> find = stockItemRepository.findStockItemsBySellingPrice(stockItem.getSellingPrice());

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier
                .create(find)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void insertStockItem() {
        StockItem stockItem = buildStockItem();

        Publisher<StockItem> setup = stockItemRepository.deleteAll().thenMany(stockItemRepository.save(stockItem));

        StepVerifier
                .create(setup)
                .consumeNextWith(foundStockItem -> {
                    assertEquals(stockItem.getStockItemId(), foundStockItem.getStockItemId());
                    assertEquals(stockItem.getDescription(), foundStockItem.getDescription());
                    assertEquals(stockItem.getSupplierName(), foundStockItem.getSupplierName());
                    assertEquals(stockItem.getQuantitySold(), foundStockItem.getQuantitySold());
                    assertEquals(stockItem.getSellingPrice(), foundStockItem.getSellingPrice());
                    assertEquals(stockItem.getCostPrice(), foundStockItem.getCostPrice());
                })
                .verifyComplete();
    }

    @Test
    public void deleteStockItemById() {

        Publisher<StockItem> setup = stockItemRepository.deleteAll().thenMany(stockItemRepository.save(buildStockItem()));
        StepVerifier
                .create(setup)
                .consumeNextWith(foundStockItem -> {
                    assertEquals(stockItem.getStockItemId(), foundStockItem.getStockItemId());
                    assertEquals(stockItem.getDescription(), foundStockItem.getDescription());
                    assertEquals(stockItem.getSupplierName(), foundStockItem.getSupplierName());
                    assertEquals(stockItem.getQuantitySold(), foundStockItem.getQuantitySold());
                    assertEquals(stockItem.getSellingPrice(), foundStockItem.getSellingPrice());
                    assertEquals(stockItem.getCostPrice(), foundStockItem.getCostPrice());
                })
                .then(this::deleteStockItemById)
                .verifyComplete();
    }


    private StockItem buildStockItem() {
        return StockItem.builder()
                .stockItemId("297445493")
                .description("Test plumbing item")
                .supplierName("Paul")
                .quantitySold(23)
                .costPrice(75.48)
                .sellingPrice(75.99)
                .quantityInStock(9723)
                .build();
    }

    private StockItemDTO buildStockItemDTO() {
        return StockItemDTO.builder()
                .stockItemId("297445493")
                .description("DTO test plumbing item")
                .supplierName("Paul")
                .quantitySold(53)
                .costPrice(75.48)
                .sellingPrice(25.99)
                .quantityInStock(9723)
                .build();
    }

}
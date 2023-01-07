package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.DataLayer.*;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
class StockControllerIntegrationTest {

    StockItem stockItem = buildStockItem();

    StockItem stockItem2 = buildStockItem2();
    String STOCK_ID = stockItem.getStockItemId();

    StockItemDTO stockItemDTO = buildStockItemDTO();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private StockItemRepository stockItemRepository;

    @Test
    void getAllStockItems() {
        Publisher<StockItem> setup = stockItemRepository.deleteAll().thenMany(stockItemRepository.save(stockItem));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/stocks")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].stockItemId").isEqualTo(stockItem.getStockItemId())
                .jsonPath("$[0].description").isEqualTo(stockItem.getDescription())
                .jsonPath("$[0].supplierName").isEqualTo(stockItem.getSupplierName())
                .jsonPath("$[0].quantitySold").isEqualTo(stockItem.getQuantitySold())
                .jsonPath("$[0].sellingPrice").isEqualTo(stockItem.getSellingPrice())
                .jsonPath("$[0].costPrice").isEqualTo(stockItem.getCostPrice());
    }

    @Test
    void insertStockItem() {
        Publisher<Void> setup = stockItemRepository.deleteAll();

        StepVerifier
                .create(setup)
                .expectNextCount(0)
                .verifyComplete();

        webTestClient
                .post()
                .uri("/stocks")
                .body(Mono.just(stockItem), StockItem.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(StockItemDTO.class)
                .value((dto) -> {
                    assertThat(dto.getDescription()).isEqualTo(stockItem.getDescription());
                    assertThat(dto.getSupplierName()).isEqualTo(stockItem.getSupplierName());
                    assertThat(dto.getQuantitySold()).isEqualTo(stockItem.getQuantitySold());
                    assertThat(dto.getSellingPrice()).isEqualTo(stockItem.getSellingPrice());
                    assertThat(dto.getCostPrice()).isEqualTo(stockItem.getCostPrice());
                });

    }

    @Test
    void getStockItemById() {
        Publisher<StockItem> setup = stockItemRepository.deleteAll().thenMany(stockItemRepository.save(stockItem));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/stocks/" + STOCK_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.stockItemId").isEqualTo(stockItem.getStockItemId())
                .jsonPath("$.description").isEqualTo(stockItem.getDescription())
                .jsonPath("$.quantitySold").isEqualTo(stockItem.getQuantitySold())
                .jsonPath("$.sellingPrice").isEqualTo(stockItem.getSellingPrice())
                .jsonPath("$.costPrice").isEqualTo(stockItem.getCostPrice())
                .jsonPath("$.supplierName").isEqualTo(stockItem.getSupplierName());
    }

    @Test
    void getStockItemByPrice() {

        StockItem stockItem = buildStockItem();
        double STOCK_PRICE = stockItem.getSellingPrice();

        Publisher<StockItem> setup = stockItemRepository.deleteAll().thenMany(stockItemRepository.save(stockItem));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient.get()
                .uri("/stocks/price/" + STOCK_PRICE)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].stockItemId").isEqualTo(stockItem.getStockItemId())
                .jsonPath("$[0].description").isEqualTo(stockItem.getDescription())
                .jsonPath("$[0].supplierName").isEqualTo(stockItem.getSupplierName())
                .jsonPath("$[0].quantitySold").isEqualTo(stockItem.getQuantitySold())
                .jsonPath("$[0].sellingPrice").isEqualTo(stockItem.getSellingPrice())
                .jsonPath("$[0].costPrice").isEqualTo(stockItem.getCostPrice());
    }

    @Test
    void updateStockItem() {
        Publisher<StockItem> setup = stockItemRepository.deleteAll().thenMany(stockItemRepository.save(stockItem2));
        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .put()
                .uri("/stocks/" + STOCK_ID)
                .body(Mono.just(stockItemDTO), StockItemDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.stockItemId").isEqualTo(stockItemDTO.getStockItemId())
                .jsonPath("$.description").isEqualTo(stockItemDTO.getDescription())
                .jsonPath("$.quantitySold").isEqualTo(stockItemDTO.getQuantitySold())
                .jsonPath("$.sellingPrice").isEqualTo(stockItemDTO.getSellingPrice())
                .jsonPath("$.costPrice").isEqualTo(stockItemDTO.getCostPrice())
                .jsonPath("$.supplierName").isEqualTo(stockItemDTO.getSupplierName());

    }

    @Test
    void deleteStockItem() {
        Publisher<StockItem> setup = stockItemRepository.deleteAll().thenMany(stockItemRepository.save(stockItem));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .delete()
                .uri("/stocks/" + STOCK_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }


    @Test
    void toStringBuilders() {
        System.out.println(StockItem.builder());
        System.out.println(StockItemDTO.builder());
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

    private StockItem buildStockItem2() {
        return StockItem.builder()
                .stockItemId("297445493")
                .description("Test another item")
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
package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.DataLayer.StockItem;
import com.um.inventoryservice.DataLayer.StockItemDTO;
import com.um.inventoryservice.DataLayer.StockItemRepository;
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
                .jsonPath("$[0].supplierId").isEqualTo(stockItem.getSupplierId())
                .jsonPath("$[0].salesQuantity").isEqualTo(stockItem.getSalesQuantity())
                .jsonPath("$[0].price").isEqualTo(stockItem.getPrice());
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
                    assertThat(dto.getSupplierId()).isEqualTo(stockItem.getSupplierId());
                    assertThat(dto.getSalesQuantity()).isEqualTo(stockItem.getSalesQuantity());
                    assertThat(dto.getPrice()).isEqualTo(stockItem.getPrice());
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
                .jsonPath("$.salesQuantity").isEqualTo(stockItem.getSalesQuantity())
                .jsonPath("$.price").isEqualTo(stockItem.getPrice())
                .jsonPath("$.supplierId").isEqualTo(stockItem.getSupplierId());
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
                .jsonPath("$.salesQuantity").isEqualTo(stockItemDTO.getSalesQuantity())
                .jsonPath("$.price").isEqualTo(stockItemDTO.getPrice())
                .jsonPath("$.supplierId").isEqualTo(stockItemDTO.getSupplierId());

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
                .supplierId(1005)
                .salesQuantity(23)
                .price(75.99)
                .build();
    }

    private StockItem buildStockItem2() {
        return StockItem.builder()
                .stockItemId("297445493")
                .description("Test another item")
                .supplierId(1005)
                .salesQuantity(23)
                .price(75.99)
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
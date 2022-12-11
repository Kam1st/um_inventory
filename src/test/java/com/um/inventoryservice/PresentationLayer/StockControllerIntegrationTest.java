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

    private final StockItem stockItem = buildStockItem();

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


    private StockItem buildStockItem() {
        return StockItem.builder()
                .description("Test plumbing item")
                .supplierId(1005)
                .salesQuantity(23)
                .price(75.99)
                .build();
    }
}
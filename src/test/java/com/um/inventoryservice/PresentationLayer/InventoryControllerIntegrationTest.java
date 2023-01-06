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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
class InventoryControllerIntegrationTest {

    private final InventoryItem inventoryItem = buildInventoryItem();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    String INVENTORY_ID = inventoryItem.getInventoryItemId();

    @Test
    void insertInventoryItem() {
        Publisher<Void> setup = inventoryItemRepository.deleteAll();

        StepVerifier
                .create(setup)
                .expectNextCount(0)
                .verifyComplete();

        webTestClient
                .post()
                .uri("/inventory")
                .body(Mono.just(inventoryItem), InventoryItemDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(InventoryItemDTO.class)
                .value((dto) -> {
                    assertEquals(dto.getInventoryItemId(), inventoryItem.getInventoryItemId());
                    assertEquals(dto.getQuantityInStock(), inventoryItem.getQuantityInStock());
                });
    }

    @Test
    void getInventoryItemById() {
        Publisher<InventoryItem> setup = inventoryItemRepository.deleteAll().thenMany(inventoryItemRepository.save(inventoryItem));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/inventory/" + INVENTORY_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.inventoryItemId").isEqualTo(inventoryItem.getInventoryItemId())
                .jsonPath("$.quantityInStock").isEqualTo(inventoryItem.getQuantityInStock());
    }

    @Test
    void getAllInventoryItems() {

        Publisher<InventoryItem> setup = inventoryItemRepository.deleteAll().thenMany(inventoryItemRepository.save(inventoryItem));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/inventory")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].inventoryItemId").isEqualTo(inventoryItem.getInventoryItemId())
                .jsonPath("$[0].quantityInStock").isEqualTo(inventoryItem.getQuantityInStock());
    }



    private InventoryItem buildInventoryItem() {
        return InventoryItem.builder()
                .inventoryItemId("1123456")
                .stockItemDTO(buildStockItemDTO())
                .quantityInStock(375)
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
                .supplierName("Paul")
                .quantitySold(53)
                .costPrice(75.48)
                .sellingPrice(25.99)
                .build();
    }
}
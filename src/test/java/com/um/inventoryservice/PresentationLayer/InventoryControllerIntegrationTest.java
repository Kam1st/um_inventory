package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.DataLayer.InventoryItem;
import com.um.inventoryservice.DataLayer.InventoryItemDTO;
import com.um.inventoryservice.DataLayer.InventoryItemRepository;
import com.um.inventoryservice.DataLayer.StockItemDTO;
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
class InventoryControllerIntegrationTest {

    private final InventoryItem inventoryItem = buildInventoryItem();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

//    @Test
//    void insertInventoryItem() {
//        Publisher<Void> setup = inventoryItemRepository.deleteAll();
//
//        StepVerifier
//                .create(setup)
//                .expectNextCount(0)
//                .verifyComplete();
//
//        webTestClient
//                .post()
//                .uri("/inventory")
//                .body(Mono.just(inventoryItem), InventoryItemDTO.class)
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                .expectBody(InventoryItemDTO.class)
//                .value((dto) -> {
//                    assertThat(dto.getStockItemDTO()).isEqualTo(inventoryItem.getStockItemDTO());
//                    assertThat(dto.getQuantityInStock()).isEqualTo(inventoryItem.getQuantityInStock());
//                });
//    }

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
                .supplierId(2005)
                .salesQuantity(53)
                .price(25.99)
                .build();
    }
}
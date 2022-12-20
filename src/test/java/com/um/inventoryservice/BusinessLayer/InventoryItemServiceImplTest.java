package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.InventoryItem;
import com.um.inventoryservice.DataLayer.InventoryItemDTO;
import com.um.inventoryservice.DataLayer.InventoryItemRepository;
import com.um.inventoryservice.DataLayer.StockItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
class InventoryItemServiceImplTest {

    @Autowired
    InventoryItemService inventoryItemService;

    @MockBean
    InventoryItemRepository inventoryItemRepository;

    StockItemDTO stockItemDTO = buildStockItemDTO();

    InventoryItem inventoryItem = buildInventoryItem();

    String INVENTORY_ID = inventoryItem.getInventoryItemId();

    InventoryItemDTO inventoryItemDTO = buildInventoryItemDTO();

    @Test
    void insertInventoryItem() {
        inventoryItemService.insertInventoryItem(Mono.just(inventoryItemDTO))
                .map(invDTO -> {
                    assertEquals(invDTO.getInventoryItemId(), inventoryItemDTO.getInventoryItemId());
                    assertEquals(invDTO.getStockItemDTO(), inventoryItemDTO.getStockItemDTO());
                    assertEquals(invDTO.getQuantityInStock(), inventoryItemDTO.getQuantityInStock());
                    return invDTO;
                });
    }

    @Test
    void getInventoryItemByInventoryItemId() {

        when(inventoryItemRepository.findInventoryItemByInventoryItemId(anyString())).thenReturn(Mono.just(inventoryItem));

        Mono<InventoryItemDTO> inventoryItemDTOMono = inventoryItemService.getInventoryItemById(INVENTORY_ID);

        StepVerifier
                .create(inventoryItemDTOMono)
                .consumeNextWith(foundInvItem -> {
                    assertEquals(inventoryItem.getInventoryItemId(), foundInvItem.getInventoryItemId());
                    assertEquals(inventoryItem.getStockItemDTO(), foundInvItem.getStockItemDTO());
                    assertEquals(inventoryItem.getQuantityInStock(), foundInvItem.getQuantityInStock());
                })

                .verifyComplete();
    }

    @Test
    void getAllInventoryItems() {

        when(inventoryItemRepository.findAll()).thenReturn(Flux.just(inventoryItem));

        Flux<InventoryItemDTO> invItemDTO = inventoryItemService.getAll();

        StepVerifier
                .create(invItemDTO)
                .consumeNextWith(foundInvItem ->{
                    assertEquals(inventoryItem.getInventoryItemId(), foundInvItem.getInventoryItemId());
                    assertEquals(inventoryItem.getStockItemDTO(), foundInvItem.getStockItemDTO());
                    assertEquals(inventoryItem.getQuantityInStock(), foundInvItem.getQuantityInStock());
                })
                .verifyComplete();
    }

    private InventoryItem buildInventoryItem() {
        return InventoryItem.builder()
                .inventoryItemId("1134567")
                .stockItemDTO(buildStockItemDTO())
                .quantityInStock(215)
                .build();
    }

    private InventoryItemDTO buildInventoryItemDTO() {
        return InventoryItemDTO.builder()
                .inventoryItemId("1124680")
                .stockItemDTO(buildStockItemDTO())
                .quantityInStock(25)
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
package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.*;
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
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureWebTestClient
class StockItemServiceImplTest {

    @Autowired
    StockItemService stockItemService;

    @Autowired
    InventoryItemService inventoryItemService;

    @MockBean
    StockItemRepository stockItemRepository;

    @MockBean
    InventoryItemRepository inventoryItemRepository;

    StockItem stockItem = buildStockItem();

    String STOCK_ID = stockItem.getStockItemId();

    StockItemDTO stockItemDTO = buildStockItemDTO();

    InventoryItem inventoryItem = buildInventoryItem();

    String INVENTORY_ID = inventoryItem.getInventoryItemId();

    InventoryItemDTO inventoryItemDTO = buildInventoryItemDTO();

    @Test
    void getAllStockItems(){

        when(stockItemRepository.findAll()).thenReturn(Flux.just(stockItem));

        Flux<StockItemDTO> stockItemDTO = stockItemService.getAll();

        StepVerifier
                .create(stockItemDTO)
                .consumeNextWith(foundStockItem ->{
                    assertEquals(stockItem.getStockItemId(), foundStockItem.getStockItemId());
                    assertEquals(stockItem.getDescription(), foundStockItem.getDescription());
                    assertEquals(stockItem.getSupplierId(), foundStockItem.getSupplierId());
                    assertEquals(stockItem.getSalesQuantity(), foundStockItem.getSalesQuantity());
                    assertEquals(stockItem.getPrice(), foundStockItem.getPrice());
                })
                .verifyComplete();
    }

    @Test
    void insertStockItem() {

        stockItemService.insertStock(Mono.just(stockItemDTO))
                .map(stockItemDTO1 -> {
                    assertEquals(stockItemDTO1.getStockItemId(), stockItem.getStockItemId());
                    assertEquals(stockItemDTO1.getDescription(), stockItem.getDescription());
                    assertEquals(stockItemDTO1.getSupplierId(), stockItem.getSupplierId());
                    assertEquals(stockItemDTO1.getSalesQuantity(), stockItem.getSalesQuantity());
                    assertEquals(stockItemDTO1.getPrice(), stockItem.getPrice());
                    return stockItemDTO1;
                });
    }

    @Test
    void getStockItemByStockItemId() {

        when(stockItemRepository.findStockItemByStockItemId(anyString())).thenReturn(Mono.just(stockItem));

        Mono<StockItemDTO> stockItemDTOMono = stockItemService.getStockItemById(STOCK_ID);

        StepVerifier
                .create(stockItemDTOMono)
                .consumeNextWith(foundStockItem -> {
                    assertEquals(stockItem.getStockItemId(), foundStockItem.getStockItemId());
                    assertEquals(stockItem.getDescription(), foundStockItem.getDescription());
                    assertEquals(stockItem.getSupplierId(), foundStockItem.getSupplierId());
                    assertEquals(stockItem.getSalesQuantity(), foundStockItem.getSalesQuantity());
                    assertEquals(stockItem.getPrice(), foundStockItem.getPrice());

                })

                .verifyComplete();
    }

    @Test
    void updateStockItem() {
        when(stockItemRepository.save(any(StockItem.class))).thenReturn(Mono.just(stockItem));

        when(stockItemRepository.findStockItemByStockItemId(anyString())).thenReturn(Mono.just(stockItem));
        stockItemService.updateStockItem(STOCK_ID, (Mono.just(stockItemDTO)))
                .map(stockItemDTO1 -> {
                    assertEquals(stockItemDTO1.getStockItemId(), stockItemDTO.getStockItemId());
                    assertEquals(stockItemDTO1.getDescription(), stockItemDTO.getDescription());
                    assertEquals(stockItemDTO1.getSupplierId(), stockItemDTO.getSupplierId());
                    assertEquals(stockItemDTO1.getSalesQuantity(), stockItemDTO.getSalesQuantity());
                    assertEquals(stockItemDTO1.getPrice(), stockItemDTO.getPrice());
                    return stockItemDTO1;
                });
    }

    @Test
    void deleteStockItem() {
        stockItemService.deleteStockItemById(STOCK_ID);
        verify(stockItemRepository, times(1)).deleteStockItemByStockItemId(STOCK_ID);
    }

    @Test
    void insertInventoryItem() {
        inventoryItemService.insertInventoryItem(Mono.just(inventoryItemDTO))
                .map(invDTO -> {
                    assertEquals(invDTO.getInventoryItemId(), inventoryItemDTO.getInventoryItemId());
                    assertEquals(invDTO.getStockItemId(), inventoryItemDTO.getStockItemId());
                    assertEquals(invDTO.getQuantityInStock(), inventoryItemDTO.getQuantityInStock());
                    return invDTO;
                });
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

    private StockItemDTO buildStockItemDTO() {
        return StockItemDTO.builder()
                .stockItemId("297445493")
                .description("DTO test plumbing item")
                .supplierId(2005)
                .salesQuantity(53)
                .price(25.99)
                .build();
    }

    private InventoryItem buildInventoryItem() {
        return InventoryItem.builder()
                .inventoryItemId("1134567")
                .stockItemId("2254321")
                .quantityInStock(215)
                .build();
    }

    private InventoryItemDTO buildInventoryItemDTO() {
        return InventoryItemDTO.builder()
                .inventoryItemId("1124680")
                .stockItemId("2297531")
                .quantityInStock(25)
                .build();
    }
}
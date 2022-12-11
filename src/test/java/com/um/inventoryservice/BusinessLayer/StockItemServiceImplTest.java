package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.StockItem;
import com.um.inventoryservice.DataLayer.StockItemDTO;
import com.um.inventoryservice.DataLayer.StockItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
class StockItemServiceImplTest {

    @Autowired
    StockItemService stockItemService;

    @MockBean
    StockItemRepository stockItemRepository;

    StockItem stockItem = buildStockItem();

    StockItemDTO stockItemDTO = buildStockItemDTO();

    @Test
    void getAllStockItems(){

        when(stockItemRepository.findAll()).thenReturn(Flux.just(stockItem));

        Flux<StockItemDTO> stockItemDTO = stockItemService.getAll();

        StepVerifier
                .create(stockItemDTO)
                .consumeNextWith(foundStockItem ->{
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

    private StockItem buildStockItem() {
        return StockItem.builder()
                .description("Test plumbing item")
                .supplierId(1005)
                .salesQuantity(23)
                .price(75.99)
                .build();
    }

    private StockItemDTO buildStockItemDTO() {
        return StockItemDTO.builder()
                .description("DTO test plumbing item")
                .supplierId(2005)
                .salesQuantity(53)
                .price(25.99)
                .build();
    }
}
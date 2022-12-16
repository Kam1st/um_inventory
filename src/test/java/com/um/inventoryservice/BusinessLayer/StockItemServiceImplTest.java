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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureWebTestClient
class StockItemServiceImplTest {

    @Autowired
    StockItemService stockItemService;

    @MockBean
    StockItemRepository stockItemRepository;

    StockItem stockItem = buildStockItem();

    String STOCK_ID = stockItem.getStockItemId();

    StockItemDTO stockItemDTO = buildStockItemDTO();

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
    void getStockItemByPrice() {
        StockItem stockItem = buildStockItem();

        double STOCK_PRICE = stockItem.getPrice();

        when(stockItemRepository.findStockItemsByPrice(anyDouble())).thenReturn(Flux.just(stockItem));

        Flux<StockItemDTO> stockItemDTO = stockItemService.getStockItemsByPrice(STOCK_PRICE);

        StepVerifier.create(stockItemDTO)
                .consumeNextWith(foundStock ->{
                    assertEquals(stockItem.getStockItemId(), foundStock.getStockItemId());
                    assertEquals(stockItem.getDescription(), foundStock.getDescription());
                    assertEquals(stockItem.getSupplierId(), foundStock.getSupplierId());
                    assertEquals(stockItem.getPrice(), foundStock.getPrice());
                })
                .verifyComplete();
    }

    @Test
    void deleteStockItem() {
        stockItemService.deleteStockItemById(STOCK_ID);
        verify(stockItemRepository, times(1)).deleteStockItemByStockItemId(STOCK_ID);
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
}
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
                    assertEquals(stockItem.getSupplierName(), foundStockItem.getSupplierName());
                    assertEquals(stockItem.getQuantitySold(), foundStockItem.getQuantitySold());
                    assertEquals(stockItem.getSellingPrice(), foundStockItem.getSellingPrice());
                })
                .verifyComplete();
    }

    @Test
    void insertStockItem() {

        stockItemService.insertStock(Mono.just(stockItemDTO))
                .map(stockItemDTO1 -> {
                    assertEquals(stockItemDTO1.getStockItemId(), stockItem.getStockItemId());
                    assertEquals(stockItemDTO1.getDescription(), stockItem.getDescription());
                    assertEquals(stockItemDTO1.getSupplierName(), stockItem.getSupplierName());
                    assertEquals(stockItemDTO1.getQuantitySold(), stockItem.getQuantitySold());
                    assertEquals(stockItemDTO1.getSellingPrice(), stockItem.getSellingPrice());
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
                    assertEquals(stockItem.getSupplierName(), foundStockItem.getSupplierName());
                    assertEquals(stockItem.getQuantitySold(), foundStockItem.getQuantitySold());
                    assertEquals(stockItem.getSellingPrice(), foundStockItem.getSellingPrice());

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
                    assertEquals(stockItemDTO1.getSupplierName(), stockItemDTO.getSupplierName());
                    assertEquals(stockItemDTO1.getQuantitySold(), stockItemDTO.getQuantitySold());
                    assertEquals(stockItemDTO1.getSellingPrice(), stockItemDTO.getSellingPrice());
                    return stockItemDTO1;
                });
    }

    @Test
    void getStockItemByPrice() {
        StockItem stockItem = buildStockItem();

        double STOCK_PRICE = stockItem.getSellingPrice();

        when(stockItemRepository.findStockItemsByPrice(anyDouble())).thenReturn(Flux.just(stockItem));

        Flux<StockItemDTO> stockItemDTO = stockItemService.getStockItemsByPrice(STOCK_PRICE);

        StepVerifier.create(stockItemDTO)
                .consumeNextWith(foundStock ->{
                    assertEquals(stockItem.getStockItemId(), foundStock.getStockItemId());
                    assertEquals(stockItem.getDescription(), foundStock.getDescription());
                    assertEquals(stockItem.getSupplierName(), foundStock.getSupplierName());
                    assertEquals(stockItem.getSellingPrice(), foundStock.getSellingPrice());
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
                .supplierName(1005)
                .quantitySold(23)
                .sellingPrice(75.99)
                .build();
    }

    private StockItemDTO buildStockItemDTO() {
        return StockItemDTO.builder()
                .stockItemId("297445493")
                .description("DTO test plumbing item")
                .supplierName(2005)
                .quantitySold(53)
                .sellingPrice(25.99)
                .build();
    }


}
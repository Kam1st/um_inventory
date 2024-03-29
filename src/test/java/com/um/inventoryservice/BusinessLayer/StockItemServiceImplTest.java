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
                    assertEquals(stockItem.getCostPrice(),foundStockItem.getCostPrice());
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
                    assertEquals(stockItemDTO1.getCostPrice(), stockItem.getCostPrice());
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
                    assertEquals(stockItem.getCostPrice(), foundStockItem.getCostPrice());

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
                    assertEquals(stockItemDTO1.getCostPrice(), stockItemDTO.getCostPrice());
                    return stockItemDTO1;
                });
    }

    @Test
    void getStockItemBySellingPrice() {
        StockItem stockItem = buildStockItem();

        double STOCK_PRICE = stockItem.getSellingPrice();

        when(stockItemRepository.findStockItemsBySellingPrice(anyDouble())).thenReturn(Flux.just(stockItem));

        Flux<StockItemDTO> stockItemDTO = stockItemService.getStockItemsByPrice(STOCK_PRICE);

        StepVerifier.create(stockItemDTO)
                .consumeNextWith(foundStock ->{
                    assertEquals(stockItem.getStockItemId(), foundStock.getStockItemId());
                    assertEquals(stockItem.getDescription(), foundStock.getDescription());
                    assertEquals(stockItem.getSupplierName(), foundStock.getSupplierName());
                    assertEquals(stockItem.getSellingPrice(), foundStock.getSellingPrice());
                    assertEquals(stockItem.getCostPrice(), foundStock.getCostPrice());
                })
                .verifyComplete();
    }
    @Test
    void getStockItemsBySupplierName() {
        StockItem stockItem = buildStockItem();

        String SUPPLIER_NAME = stockItem.getSupplierName();

        when(stockItemRepository.findStockItemsBySupplierName(anyString())).thenReturn(Flux.just(stockItem));

        Flux<StockItemDTO> stockItemDTO = stockItemService.getStockItemsBySupplierName(SUPPLIER_NAME);

        StepVerifier.create(stockItemDTO)
                .consumeNextWith(foundStock ->{
                    assertEquals(stockItem.getStockItemId(), foundStock.getStockItemId());
                    assertEquals(stockItem.getDescription(), foundStock.getDescription());
                    assertEquals(stockItem.getSupplierName(), foundStock.getSupplierName());
                    assertEquals(stockItem.getSellingPrice(), foundStock.getSellingPrice());
                    assertEquals(stockItem.getCostPrice(), foundStock.getCostPrice());
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
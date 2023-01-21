package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.StockItemDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockItemService {
    Flux<StockItemDTO> getAll();
    Flux<StockItemDTO> getAllStockItemsByQuantitySold();
    Mono<StockItemDTO> insertStock(Mono<StockItemDTO> stockItemMono);
    Mono<StockItemDTO> getStockItemById(String stockItemId);
    Flux<StockItemDTO> getStockItemsByPrice(double price);
    Mono<StockItemDTO> updateStockItem(String stockItemId, Mono<StockItemDTO> stockItemDTOMono);
    Mono<Void> deleteStockItemById(String stockItemId);
}

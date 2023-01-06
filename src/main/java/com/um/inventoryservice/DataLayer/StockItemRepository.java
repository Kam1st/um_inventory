package com.um.inventoryservice.DataLayer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface StockItemRepository extends ReactiveMongoRepository<StockItem, String> {
    Mono<StockItem> findStockItemByStockItemId(String stockItemId);
    Flux<StockItem> findStockItemsBySellingPrice(double sellingPrice);
    Mono<Void> deleteStockItemByStockItemId(String stockItemId);
}

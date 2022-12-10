package com.um.inventoryservice.DataLayer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StockItemRepository extends ReactiveMongoRepository<StockItem, String> {
}

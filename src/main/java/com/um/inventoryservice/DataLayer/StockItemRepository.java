package com.um.inventoryservice.DataLayer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockItemRepository extends ReactiveMongoRepository<StockItem, String> {
}

package com.um.inventoryservice.DataLayer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface InventoryItemRepository extends ReactiveMongoRepository<InventoryItem, String> {


}

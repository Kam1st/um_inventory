package com.um.inventoryservice.DataLayer;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String>  {

    @Query("{'stockOrderDTOS.stockItemId': ?0}")
    Flux<Order> findOrdersByStockItemId(String stockItemId);
    @Query("{'stockOrderDTOS.quantity': ?0}")
    Flux<Order> findOrdersByQuantitySold(int quantity);
    @Query("{'stockOrderDTOS':?0}")
    Flux<StockOrderDTO> findAllStockOrders();
    Flux<Order> findOrdersByClientId(String clientId);
}

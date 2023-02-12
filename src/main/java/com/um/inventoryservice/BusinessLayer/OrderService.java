package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.OrderDTO;
import com.um.inventoryservice.DataLayer.StockOrderDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface OrderService {

    Flux<OrderDTO> getAll();
    Flux<OrderDTO> getOrdersByStockItemId(String stockItemId);
    Mono<OrderDTO> insertOrder(Mono<OrderDTO> orderDTOMono);
    Flux<OrderDTO> getOrdersByClientId(String clientId);
    public Flux<StockOrderDTO> getStockOrdersByQuantity();
    public Flux<StockOrderDTO> getStockOrdersByQuantityByClient(String clientId);


}

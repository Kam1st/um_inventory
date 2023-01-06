package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.OrderDTO;
import com.um.inventoryservice.DataLayer.StockItemDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface OrderService {

    Flux<OrderDTO> getOrdersByStockItemId(String stockItemId);

    Mono<OrderDTO> insertOrder(Mono<OrderDTO> orderDTOMono);


}

package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.*;
import com.um.inventoryservice.util.EntityDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Flux<OrderDTO> getOrdersByStockItemId(String stockItemId){
        return orderRepository.findOrdersByStockItemId(stockItemId)
                .map(EntityDTOUtil::toDTO);
    }

    @Override
    public Mono<OrderDTO> insertOrder(Mono<OrderDTO> orderDTOMono) {
        return orderDTOMono
                .map(EntityDTOUtil::toEntity)
                .flatMap((orderRepository::save))
                .map(EntityDTOUtil::toDTO);
    }
}

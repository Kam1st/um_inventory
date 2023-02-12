package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.*;
import com.um.inventoryservice.util.EntityDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Flux<OrderDTO> getAll() {
        return orderRepository.findAll()
                .map(EntityDTOUtil::toDTO);
    }

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

    @Override
    public Flux<OrderDTO> getOrdersByClientId(String clientId){
        return orderRepository.findOrdersByClientId(clientId)
                .map(EntityDTOUtil::toDTO);
    }

    @Override
    public Flux<StockOrderDTO> getStockOrdersByQuantity() {
        return orderRepository.findAll()
                .flatMapIterable(order -> order.getStockOrderDTOS())
                .sort((o1, o2) -> Integer.compare(o2.getQuantity(), o1.getQuantity()))
                .collectList()
                .flatMapMany(Flux::fromIterable);
    }


    @Override
    public Flux<StockOrderDTO> getStockOrdersByQuantityByClient(String clientId) {
        return orderRepository.findOrdersByClientId(clientId)
                .flatMapIterable(Order::getStockOrderDTOS)
                .sort((o1, o2) -> Integer.compare(o2.getQuantity(), o1.getQuantity()))
                .collectList()
                .flatMapMany(Flux::fromIterable);
    }

}

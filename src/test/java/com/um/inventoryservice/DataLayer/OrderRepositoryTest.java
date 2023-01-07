package com.um.inventoryservice.DataLayer;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@DataMongoTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    Order order = buildOrder();

    OrderDTO orderDTO = buildOrderDTO();
    @Test
    void findOrdersByStockItemId() {

        Publisher<Order> setup = orderRepository.deleteAll().thenMany(orderRepository.save(order));
        Publisher<Order> find = orderRepository.findOrdersByStockItemId(order.getStockOrderDTOS().get(0).getStockItemId());

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier
                .create(find)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void findOrdersByClientId() {

        Publisher<Order> setup = orderRepository.deleteAll().thenMany(orderRepository.save(order));
        Publisher<Order> find = orderRepository.findOrdersByClientId(order.getClientId());

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier
                .create(find)
                .expectNextCount(1)
                .verifyComplete();
    }
    private Order buildOrder() {
        return Order.builder()
                .clientId("297445493")
                .stockOrderDTOS(List.of(new StockOrderDTO("2454544", "this is the test for stock item 1", 6)))
                .build();
    }

    private OrderDTO buildOrderDTO() {
        return OrderDTO.builder()
                .clientId("297445493")
                .stockOrderDTOS(List.of(new StockOrderDTO("2454544", "this is the test for stock item 1", 6)))
                .build();
    }
}
package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    @MockBean
    OrderRepository orderRepository;

    Order order = buildOrder();

    OrderDTO orderDTO = buildOrderDTO();

    @Test
    void getAllOrders() {
        when(orderRepository.findAll()).thenReturn(Flux.just(order));

        Flux<OrderDTO> orderDTO = orderService.getAll();

        StepVerifier
                .create(orderDTO)
                .consumeNextWith(foundOrder ->{
                    assertEquals(order.getClientId(), foundOrder.getClientId());
                    assertEquals(order.getStockOrderDTOS().size(), foundOrder.getStockOrderDTOS().size());
                })
                .verifyComplete();
    }

    @Test
    void getOrdersByStockItemId() {

        when(orderRepository.findOrdersByStockItemId(anyString())).thenReturn(Flux.just(order));

        Flux<OrderDTO> orderDTO = orderService.getOrdersByStockItemId("");

        StepVerifier
                .create(orderDTO)
                .consumeNextWith(foundOrder ->{
                    assertEquals(order.getClientId(), foundOrder.getClientId());
                    assertEquals(order.getStockOrderDTOS().size(), foundOrder.getStockOrderDTOS().size());
                })
                .verifyComplete();
    }

    @Test
    void getOrdersByClientId() {

        when(orderRepository.findOrdersByClientId(anyString())).thenReturn(Flux.just(order));

        Flux<OrderDTO> orderDTO = orderService.getOrdersByClientId("");

        StepVerifier
                .create(orderDTO)
                .consumeNextWith(foundOrder ->{
                    assertEquals(order.getClientId(), foundOrder.getClientId());
                    assertEquals(order.getStockOrderDTOS().size(), foundOrder.getStockOrderDTOS().size());
                })
                .verifyComplete();
    }

    @Test
    void insertOrder() {

        orderService.insertOrder(Mono.just(orderDTO))
                .map(orderDTO1 -> {
                    assertEquals(orderDTO1.getClientId(), order.getClientId());
                    assertEquals(order.getStockOrderDTOS().size(), order.getStockOrderDTOS().size());
                    return orderDTO1;
                });
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
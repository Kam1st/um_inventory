package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.DataLayer.*;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
class OrderControllerIntegrationTest {

    private final Order order = buildOrder();

    private final OrderDTO orderDTO = buildOrderDTO();

    String ORDER_ID = order.getOrderId();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void getAllOrders() {
        Publisher<Order> setup = orderRepository.deleteAll().thenMany(orderRepository.save(order));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/orders")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].clientId").isEqualTo(order.getClientId())
                .jsonPath("$[0].stockOrderDTOS").isNotEmpty();
    }
    @Test
    void insertOrder() {
        Publisher<Void> setup = orderRepository.deleteAll();

        StepVerifier
                .create(setup)
                .expectNextCount(0)
                .verifyComplete();

        webTestClient
                .post()
                .uri("/orders")
                .body(Mono.just(order), OrderDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderDTO.class)
                .value((dto) -> {
                    assertEquals(dto.getClientId(), order.getClientId());
                    assertEquals(dto.getStockOrderDTOS().size(), order.getStockOrderDTOS().size());
                });
    }

    @Test
    void getOrderById() {
        Publisher<Order> setup = orderRepository.deleteAll().thenMany(orderRepository.save(order));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/orders/" + ORDER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.orderId").isEqualTo(orderDTO.getOrderId())
                .jsonPath("$.clientId").isEqualTo(orderDTO.getClientId());
//                .jsonPath("$.stockOrderDTOS").isEqualTo(orderDTO.getStockOrderDTOS());

    }

    @Test
    void getOrdersByStockItemId() {
        Publisher<Order> setup = orderRepository.deleteAll().thenMany(orderRepository.save(order));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/orders/stockItem/" + order.getStockOrderDTOS().get(0).getStockItemId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].clientId").isEqualTo(order.getClientId())
                .jsonPath("$[0].stockOrderDTOS").isNotEmpty();
    }

    @Test
    void getOrdersByClientId() {
        Publisher<Order> setup = orderRepository.deleteAll().thenMany(orderRepository.save(order));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/orders/client/" + order.getClientId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].clientId").isEqualTo(order.getClientId())
                .jsonPath("$[0].stockOrderDTOS").isNotEmpty();
    }

    @Test
    void updateOrder() {
        Publisher<Order> setup = orderRepository.deleteAll().thenMany(orderRepository.save(order));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .put()
                .uri("/orders/" + ORDER_ID)
                .body(Mono.just(orderDTO), OrderDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.orderId").isEqualTo(orderDTO.getOrderId())
                .jsonPath("$.clientId").isEqualTo(orderDTO.getClientId());
//                .jsonPath("$.stockOrderDTOS").isEqualTo(orderDTO.getStockOrderDTOS());
    }


    @Test
    void getStockOrdersByQuantity() {
        Publisher<Order> setup = orderRepository.deleteAll().thenMany(orderRepository.save(order));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient.get().uri("/orders/quantity")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StockOrderDTO.class)
                .hasSize(1)
                .consumeWith(stockList -> {
                    List<StockOrderDTO> orders = stockList.getResponseBody();
                    assertThat(orders).extracting("quantity").contains(6);
                });
    }

    @Test
    void getStockOrdersByQuantityByClient() {
        Publisher<Order> setup = orderRepository.deleteAll().thenMany(orderRepository.save(order));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient.get().uri("/orders/297445493/quantity")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StockOrderDTO.class)
                .hasSize(1)
                .consumeWith(stockList -> {
                    List<StockOrderDTO> orders = stockList.getResponseBody();
                    assertThat(orders).extracting("quantity").contains(6);
                });
    }

    @Test
    void toStringBuilders() {
        System.out.println(Order.builder());
        System.out.println(OrderDTO.builder());
        System.out.println(StockOrderDTO.builder());
    }

    private Order buildOrder() {
        return Order.builder()
                .orderId("9753157")
                .clientId("297445493")
                .stockOrderDTOS(List.of(new StockOrderDTO("2454544", "this is the test for stock item 1", 6)))
                .build();
    }

    private OrderDTO buildOrderDTO() {
        return OrderDTO.builder()
                .orderId("9753157")
                .clientId("297445493")
                .stockOrderDTOS(List.of(new StockOrderDTO("2454544", "this is the test for stock item 1", 6)))
                .build();
    }
}
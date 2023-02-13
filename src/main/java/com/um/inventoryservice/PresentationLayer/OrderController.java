package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.BusinessLayer.OrderService;
import com.um.inventoryservice.DataLayer.OrderDTO;
import com.um.inventoryservice.DataLayer.StockOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping()
    public Flux<OrderDTO> getAllOrders() {
        return orderService.getAll();
    }

    @GetMapping("/{orderId}")
    public Mono<ResponseEntity<OrderDTO>> getOrderById(@PathVariable String orderId) {
        return orderService
                .getOrderById(orderId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<OrderDTO> insertOrder(@RequestBody Mono<OrderDTO> orderDTOMono) {
        return orderService.insertOrder(orderDTOMono);
    }

    @GetMapping("/stockItem/{stockItemId}")
    public Flux<OrderDTO> getOrdersByStockItemId(@PathVariable String stockItemId) {
        return orderService.getOrdersByStockItemId(stockItemId);
    }

    @GetMapping("/client/{clientId}")
    public Flux<OrderDTO> getOrdersByClientId(@PathVariable String clientId) {
        return orderService.getOrdersByClientId(clientId);
    }

    @PutMapping("/{orderId}")
    public Mono<ResponseEntity<OrderDTO>> updateOrder(@PathVariable String orderId, @RequestBody Mono<OrderDTO> orderDTOMono) {
        return orderService.updateOrder(orderId, orderDTOMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/quantity")
    public Mono<ResponseEntity<Flux<StockOrderDTO>>> getStockOrdersByQuantity() {
        Flux<StockOrderDTO> stockOrders = orderService.getStockOrdersByQuantity();
        return Mono.just(ResponseEntity.ok().body(stockOrders));
        }

    @GetMapping("/{clientId}/quantity")
    public Mono<ResponseEntity<Flux<StockOrderDTO>>> getStockOrdersByQuantityByClient(@PathVariable String clientId) {
        Flux<StockOrderDTO> stockOrders = orderService.getStockOrdersByQuantityByClient(clientId);
        return Mono.just(ResponseEntity.ok().body(stockOrders));
    }

}

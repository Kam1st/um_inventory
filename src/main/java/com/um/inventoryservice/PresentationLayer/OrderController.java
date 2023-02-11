package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.BusinessLayer.OrderService;
import com.um.inventoryservice.DataLayer.OrderDTO;
import com.um.inventoryservice.DataLayer.StockItemDTO;
import com.um.inventoryservice.DataLayer.StockOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.Arrays.stream;

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

    @PostMapping
    public Mono<OrderDTO> insertOrder(@RequestBody Mono<OrderDTO> orderDTOMono) {
        return orderService.insertOrder(orderDTOMono);
    }

//    @GetMapping("/stockItem/quantitySold")
//    public Flux<StockOrderDTO> getStockOrdersByQuantity() {
//        Flux<StockOrderDTO> stockOrderList = new Flux<StockOrderDTO>() {
//            @Override
//            public void subscribe(CoreSubscriber<? super StockOrderDTO> actual) {
//
//            }
//        }
//        return stockOrderList;
//    }

    @GetMapping("/quantity")
        public Mono<ResponseEntity<Flux<StockOrderDTO>>> getStockOrdersByQuantity() {
            Flux<StockOrderDTO> stockOrders = orderService.getStockOrdersByQuantity();
            return Mono.just(ResponseEntity.ok().body(stockOrders));
        }

    @GetMapping("/stockItem/{stockItemId}")
    public Flux<OrderDTO> getOrdersByStockItemId(@PathVariable String stockItemId) {
        return orderService.getOrdersByStockItemId(stockItemId);
    }

    @GetMapping("/client/{clientId}")
    public Flux<OrderDTO> getOrdersByClientId(@PathVariable String clientId) {
        return orderService.getOrdersByClientId(clientId);
    }



}

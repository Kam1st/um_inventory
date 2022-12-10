package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.BusinessLayer.StockItemService;
import com.um.inventoryservice.DataLayer.StockItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("stocks")
public class StockController {
    @Autowired
    StockItemService stockItemService;

    @GetMapping()
    public Flux<StockItemDTO> getAllStock() {
        return stockItemService.getAll();
    }

    @PostMapping
    public Mono<StockItemDTO> insertStock(@RequestBody Mono<StockItemDTO> stockItemDTOMono) {
        return stockItemService.insertStock(stockItemDTOMono);
    }
}

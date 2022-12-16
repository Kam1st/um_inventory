package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.BusinessLayer.StockItemService;
import com.um.inventoryservice.DataLayer.StockItemDTO;
import com.um.inventoryservice.DataLayer.StockItemRepository;
import com.um.inventoryservice.util.EntityDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("{stockItemId}")
    public Mono<ResponseEntity<StockItemDTO>> getStockItemById(@PathVariable String stockItemId) {
        return stockItemService
                .getStockItemById(stockItemId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Mono<StockItemDTO> insertStock(@RequestBody Mono<StockItemDTO> stockItemDTOMono) {
        return stockItemService.insertStock(stockItemDTOMono);
    }

    @GetMapping("/stocks/{price}")
    public Flux<StockItemDTO> getStockItemByPrice(@PathVariable double price) {
        return stockItemService.getStockItemsByPrice(price);
    }

    @PutMapping("{stockItemId}")
    public Mono<ResponseEntity<StockItemDTO>> updateStockItemById(@PathVariable String stockItemId, @RequestBody Mono<StockItemDTO> stockItemDTOMono) {
        return stockItemService.updateStockItem(stockItemId, stockItemDTOMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{stockItemId}")
    public Mono<Void> deleteStockItem(@PathVariable String stockItemId) {
        return stockItemService.deleteStockItemById(stockItemId);
    }

}

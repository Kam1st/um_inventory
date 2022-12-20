package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.BusinessLayer.InventoryItemService;
import com.um.inventoryservice.DataLayer.InventoryItem;
import com.um.inventoryservice.DataLayer.InventoryItemDTO;
import com.um.inventoryservice.DataLayer.StockItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("inventory")
public class InventoryController {

    @Autowired
    InventoryItemService inventoryItemService;

    @GetMapping()
    public Flux<InventoryItemDTO> getAllStock() {
        return inventoryItemService.getAll();
    }

    @GetMapping("{inventoryItemId}")
    public Mono<ResponseEntity<InventoryItemDTO>> getInventoryItemById(@PathVariable String inventoryItemId) {
        return inventoryItemService
                .getInventoryItemById(inventoryItemId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Mono<InventoryItemDTO> insertInventoryItem(@RequestBody Mono<InventoryItemDTO> inventoryItemDTO) {
        return inventoryItemService.insertInventoryItem(inventoryItemDTO);
    }
}

package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.InventoryItem;
import com.um.inventoryservice.DataLayer.InventoryItemDTO;
import com.um.inventoryservice.DataLayer.StockItemDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InventoryItemService {

    Mono<InventoryItemDTO> insertInventoryItem(Mono<InventoryItemDTO> inventoryItemDTOMono);
    Flux<InventoryItemDTO> getAll();

    Mono<InventoryItemDTO> getInventoryItemById(String inventoryItemId);
}

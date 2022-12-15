package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.InventoryItem;
import com.um.inventoryservice.DataLayer.InventoryItemDTO;
import reactor.core.publisher.Mono;

public interface InventoryItemService {

    Mono<InventoryItemDTO> insertInventoryItem(Mono<InventoryItemDTO> inventoryItemDTOMono);
}

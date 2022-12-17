package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.BusinessLayer.InventoryItemService;
import com.um.inventoryservice.DataLayer.InventoryItem;
import com.um.inventoryservice.DataLayer.InventoryItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("inventory")
public class InventoryController {

    @Autowired
    InventoryItemService inventoryItemService;

    @PostMapping
    public Mono<InventoryItemDTO> insertInventoryItem(@RequestBody Mono<InventoryItemDTO> inventoryItemDTO) {
        return inventoryItemService.insertInventoryItem(inventoryItemDTO);
    }
}

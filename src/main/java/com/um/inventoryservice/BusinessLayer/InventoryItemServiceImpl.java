package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.InventoryItemDTO;
import com.um.inventoryservice.DataLayer.InventoryItemRepository;
import com.um.inventoryservice.util.EntityDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class InventoryItemServiceImpl implements InventoryItemService{

    @Autowired
    InventoryItemRepository inventoryItemRepository;

    @Override
    public Mono<InventoryItemDTO> insertInventoryItem(Mono<InventoryItemDTO> inventoryItemDTOMono) {
        return inventoryItemDTOMono
                .map(EntityDTOUtil::toEntity)
//                .doOnNext(e -> e.(EntityDtoUtil.generateInventoryId()))
                .flatMap((inventoryItemRepository::save))
                .map(EntityDTOUtil::toDTO);
    }
}

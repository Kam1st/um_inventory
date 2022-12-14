package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.StockItemDTO;
import com.um.inventoryservice.DataLayer.StockItemRepository;
import com.um.inventoryservice.util.EntityDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StockItemServiceImpl implements StockItemService{

    @Autowired
    StockItemRepository stockItemRepository;

    @Override
    public Flux<StockItemDTO> getAll() {
        return stockItemRepository.findAll()
                .map(EntityDTOUtil::toDTO);
    }

    @Override
    public Mono<StockItemDTO> getStockItemById(String stockItemId) {
        return stockItemRepository.findStockItemById(stockItemId)
                .map(EntityDTOUtil::toDTO);
    }
    @Override
    public Mono<StockItemDTO> insertStock(Mono<StockItemDTO> stockItemDTOMono) {
        return stockItemDTOMono
                .map(EntityDTOUtil::toEntity)
//                .doOnNext(e -> e.(EntityDtoUtil.generateStockId()))
                .flatMap((stockItemRepository::save))
                .map(EntityDTOUtil::toDTO);
    }
}

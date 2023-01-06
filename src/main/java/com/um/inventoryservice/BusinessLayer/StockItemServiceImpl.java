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
        return stockItemRepository.findStockItemByStockItemId(stockItemId)
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

    @Override
    public Flux<StockItemDTO> getStockItemsByPrice(double sellingPrice) {
        return stockItemRepository.findStockItemsBySellingPrice(sellingPrice)
                .map(EntityDTOUtil::toDTO);
    }

    @Override
    public Mono<StockItemDTO> updateStockItem(String stockItemId, Mono<StockItemDTO> stockItemDTOMono){
        return stockItemRepository.findStockItemByStockItemId(stockItemId)
                .flatMap(p -> stockItemDTOMono
                        .map(EntityDTOUtil::toEntity)
                        .doOnNext(e -> e.setStockItemId(p.getStockItemId()))
                        .doOnNext(e -> e.setId(p.getId()))
                )
                .flatMap(stockItemRepository::save)
                .map(EntityDTOUtil::toDTO);
    }

    @Override
    public Mono<Void> deleteStockItemById(String stockItemId) {
        return stockItemRepository.deleteStockItemByStockItemId(stockItemId);
    }

}

package com.um.inventoryservice.util;

import com.um.inventoryservice.DataLayer.StockItem;
import com.um.inventoryservice.DataLayer.StockItemDTO;
import lombok.Generated;
import org.springframework.beans.BeanUtils;

import java.util.Random;

public class EntityDTOUtil {

    @Generated
    public EntityDTOUtil(){}

    public static StockItem toEntity(StockItemDTO dto) {
        StockItem stockItem = new StockItem();
        stockItem.setStockItemId(generateStockId());
        stockItem.setDescription(dto.getDescription());
        stockItem.setPrice(dto.getPrice());
        stockItem.setSupplierId(dto.getSupplierId());
        stockItem.setSalesQuantity(dto.getSalesQuantity());
        return stockItem;
    }

    public static String generateStockId() {
        Random random = new Random();
        int number = random.nextInt(99999);
        return "22" + (String.format("%05d", number));
    }

    public static StockItemDTO toDTO(StockItem stockItem) {
        StockItemDTO dto = new StockItemDTO();
        BeanUtils.copyProperties(stockItem, dto);
        return dto;
    }
}

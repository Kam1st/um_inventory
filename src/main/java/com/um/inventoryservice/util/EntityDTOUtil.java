package com.um.inventoryservice.util;

import com.um.inventoryservice.DataLayer.*;
import lombok.Generated;
import org.springframework.beans.BeanUtils;

public class EntityDTOUtil {

    @Generated
    public EntityDTOUtil(){}

    public static StockItem toEntity(StockItemDTO dto) {
        StockItem stockItem = new StockItem();
        stockItem.setStockItemId(dto.getStockItemId());
        stockItem.setDescription(dto.getDescription());
        stockItem.setSellingPrice(dto.getSellingPrice());
        stockItem.setCostPrice(dto.getCostPrice());
        stockItem.setSupplierName(dto.getSupplierName());
        stockItem.setQuantitySold(dto.getQuantitySold());
        return stockItem;
    }

//    public static String generateStockId() {
//        Random random = new Random();
//        int number = random.nextInt(99999);
//        return "22" + (String.format("%05d", number));
//    }

    public static StockItemDTO toDTO(StockItem stockItem) {
        StockItemDTO dto = new StockItemDTO();
        BeanUtils.copyProperties(stockItem, dto);
        return dto;
    }

    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setStockOrderDTOS(order.getStockOrderDTOS());
        dto.setClientId(order.getClientId());
        return dto;
    }

    public static Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setStockOrderDTOS(dto.getStockOrderDTOS());
        order.setClientId(dto.getClientId());
        return order;
    }

    public static InventoryItem toEntity(InventoryItemDTO inventoryItemDTO) {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setInventoryItemId(inventoryItemDTO.getInventoryItemId());
        inventoryItem.setStockItemDTO(inventoryItemDTO.getStockItemDTO());
        inventoryItem.setQuantityInStock(inventoryItemDTO.getQuantityInStock());
        return inventoryItem;
    }

    public static InventoryItemDTO toDTO(InventoryItem inventoryItem) {
        InventoryItemDTO inventoryItemDTO = new InventoryItemDTO();
        BeanUtils.copyProperties(inventoryItem, inventoryItemDTO);
        return inventoryItemDTO;
    }

//    public static String generateInventoryId() {
//        Random random = new Random();
//        int number = random.nextInt(99999);
//        return "11" + (String.format("%05d", number));
//    }
}
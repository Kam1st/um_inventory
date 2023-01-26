package com.um.inventoryservice.util;

import com.um.inventoryservice.DataLayer.*;
import lombok.Generated;
import org.springframework.beans.BeanUtils;

import java.util.Random;
import java.util.UUID;

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
        stockItem.setQuantityInStock(dto.getQuantityInStock());
        return stockItem;
    }


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
    public static ClientDTO toDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setClientId(client.getClientId());
        dto.setClientName(client.getClientName());
        dto.setClientEmployeeName(client.getClientEmployeeName());
        dto.setClientAddress(client.getClientAddress());
        dto.setClientPhone(client.getClientPhone());
        return dto;
    }
    public static Client toEntity(ClientDTO dto) {
        Client client = new Client();
        client.setClientId(dto.getClientId());
        client.setClientName(dto.getClientName());
        client.setClientEmployeeName(dto.getClientEmployeeName());
        client.setClientAddress(dto.getClientAddress());
        client.setClientPhone(dto.getClientPhone());
        return client;
    }

    public static EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setEmployeeName(employee.getEmployeeName());
        dto.setPosition(employee.getPosition());
        dto.setStatus(employee.getStatus());
        dto.setDateOfHire(employee.getDateOfHire());
        return dto;
    }

    public static Employee toEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setEmployeeId(dto.getEmployeeId());
        employee.setEmployeeName(dto.getEmployeeName());
        employee.setPosition(dto.getPosition());
        employee.setStatus(dto.getStatus());
        employee.setDateOfHire(dto.getDateOfHire());
        return employee;
    }

    public static String generateEmployeeString() {
        Random random = new Random();
        int number = random.nextInt(9999);
        return (String.format("%05d", number));
    }

    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}

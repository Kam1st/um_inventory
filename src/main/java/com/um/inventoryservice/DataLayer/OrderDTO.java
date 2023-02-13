package com.um.inventoryservice.DataLayer;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private String orderId;
    private String clientId;
    private List<StockOrderDTO> stockOrderDTOS;

    public OrderDTO(String clientId, List<StockOrderDTO> stockOrderDTOS) {
        this.clientId = clientId;
        this.stockOrderDTOS = stockOrderDTOS;
    }
}
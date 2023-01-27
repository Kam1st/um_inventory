package com.um.inventoryservice.DataLayer;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private String clientId;
    private List<StockOrderDTO> stockOrderDTOS;

}
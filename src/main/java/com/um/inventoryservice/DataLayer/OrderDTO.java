package com.um.inventoryservice.DataLayer;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private String clientId;
    private List<StockOrderDTO> stockOrderDTOS;

}
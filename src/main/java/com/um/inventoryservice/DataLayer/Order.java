package com.um.inventoryservice.DataLayer;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;

    private String clientId;
    private List<StockOrderDTO> stockOrderDTOS;
}

package com.um.inventoryservice.DataLayer;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockOrderDTO {

    private String stockItemId;
    private String description;
    private int quantity;
}

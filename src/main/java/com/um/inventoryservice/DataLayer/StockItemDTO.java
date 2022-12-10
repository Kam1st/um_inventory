package com.um.inventoryservice.DataLayer;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockItemDTO {
    private String stockItemId;
    private String description;
    private int supplierId;
    private int salesQuantity;
    private double price;
}

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
    private String supplierName;
    private int quantitySold;
    private double costPrice;
    private double sellingPrice;
    private int quantity;
}

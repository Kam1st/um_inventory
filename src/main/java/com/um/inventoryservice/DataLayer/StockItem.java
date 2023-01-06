package com.um.inventoryservice.DataLayer;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockItem {

    @Id
    private String id;

    private String stockItemId;
    private String description;
    private String supplierName;
    private int quantitySold;
    private double costPrice;
    private double sellingPrice;
    private int quantity;
}

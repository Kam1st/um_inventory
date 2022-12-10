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
    private int supplierId;
    private int salesQuantity;
    private double price;
}

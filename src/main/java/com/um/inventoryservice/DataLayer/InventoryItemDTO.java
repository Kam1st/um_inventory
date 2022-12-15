package com.um.inventoryservice.DataLayer;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemDTO {

    private String inventoryItemId;
    private String stockItemId;
    private int quantityInStock;
}

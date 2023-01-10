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
    private int quantityInStock;

    public StockItemDTO(String description, String supplierName, int quantitySold, double costPrice, double sellingPrice,int quantityInStock) {
        this.description = description;
        this.supplierName = supplierName;
        this.quantitySold = quantitySold;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.quantityInStock = quantityInStock;
    }
}

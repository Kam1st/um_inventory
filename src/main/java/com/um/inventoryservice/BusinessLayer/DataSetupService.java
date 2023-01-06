package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.InventoryItemDTO;
import com.um.inventoryservice.DataLayer.StockItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    StockItemService stockItemService;

    @Autowired
    InventoryItemService inventoryItemService;

    @Override
    public void run(String... args) throws Exception {

        StockItemDTO s1 = new StockItemDTO("2454544", "Test stock item 1", 1, 3864, 28.6, 28.6, 9);
        StockItemDTO s2 = new StockItemDTO("7486504", "Test stock item 2", 1, 9736, 25.99, 25.99, 5);
        StockItemDTO s3 = new StockItemDTO("9735693", "Test stock item 3", 2, 7344, 39.6, 39.6, 85);
        StockItemDTO s4 = new StockItemDTO("9736560", "Test stock item 4", 2, 3567, 25.99, 25.99, 9865);
        StockItemDTO s5 = new StockItemDTO("9576508", "Test stock item 5", 3, 6466, 63.6, 63.6, 64);
        StockItemDTO s6 = new StockItemDTO("5875693", "Test stock item 6", 3, 6775, 25.99, 25.99, 85);
        StockItemDTO s7 = new StockItemDTO("4974659", "Test stock item 7", 4, 3456, 49.6, 49.6, 85);
        StockItemDTO s8 = new StockItemDTO("3085708", "Test stock item 8", 5, 8536, 25.99, 25.99, 73);
        StockItemDTO s9 = new StockItemDTO("9837058", "Test stock item 9", 5, 4674, 76.6, 76.6, 96);
        StockItemDTO s10 = new StockItemDTO("6387508", "Test stock item 10", 6, 5675, 25.99, 25.99, 74);

        Flux.just(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10)
                .flatMap(p -> stockItemService.insertStock(Mono.just(p))
                        .log(p.toString()))
                .subscribe();

//        InventoryItemDTO inv1 = new InventoryItemDTO("1122222", new StockItemDTO("2454544", "Test stock item 1", 1, 3864, 28.6), 250);
//        InventoryItemDTO inv2 = new InventoryItemDTO("1133333",new StockItemDTO("7486504", "Test stock item 2", 1, 9736, 25.99), 24);
//        InventoryItemDTO inv3 = new InventoryItemDTO("1144444",new StockItemDTO("9735693", "Test stock item 3", 2, 7344, 39.6), 183);
//        InventoryItemDTO inv4 = new InventoryItemDTO("1155555", new StockItemDTO("9736560", "Test stock item 4", 2, 3567, 25.99), 212);
//        InventoryItemDTO inv5 = new InventoryItemDTO("1166666", new StockItemDTO("9576508", "Test stock item 5", 3, 6466, 63.6), 75);
//        InventoryItemDTO inv6 = new InventoryItemDTO("1177777", new StockItemDTO("5875693", "Test stock item 6", 3, 6775, 25.99),195);
//        InventoryItemDTO inv7 = new InventoryItemDTO("1188888", new StockItemDTO("4974659", "Test stock item 7", 4, 3456, 49.6),500);
//        InventoryItemDTO inv8 = new InventoryItemDTO("1199999", new StockItemDTO("3085708", "Test stock item 8", 5, 8536, 25.99),795);
//        InventoryItemDTO inv9 = new InventoryItemDTO("1135791", new StockItemDTO("9837058", "Test stock item 9", 5, 4674, 76.6),45);
//        InventoryItemDTO inv10 = new InventoryItemDTO("1124680", new StockItemDTO("6387508", "Test stock item 10", 6, 5675, 25.99),325);
//
//        Flux.just(inv1, inv2, inv3, inv4, inv5, inv6, inv7, inv8, inv9, inv10)
//                .flatMap(p -> inventoryItemService.insertInventoryItem(Mono.just(p))
//                        .log(p.toString()))
//                .subscribe();

    }
}

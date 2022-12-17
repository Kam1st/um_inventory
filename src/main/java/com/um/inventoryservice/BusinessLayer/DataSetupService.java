package com.um.inventoryservice.BusinessLayer;

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

    @Override
    public void run(String... args) throws Exception {

        StockItemDTO s1 = new StockItemDTO("2454544", "Test stock item 1", 1, 3864, 28.6);
        StockItemDTO s2 = new StockItemDTO("7486504", "Test stock item 2", 1, 9736, 25.99);
        StockItemDTO s3 = new StockItemDTO("9735693", "Test stock item 3", 2, 7344, 39.6);
        StockItemDTO s4 = new StockItemDTO("9736560", "Test stock item 4", 2, 3567, 25.99);
        StockItemDTO s5 = new StockItemDTO("9576508", "Test stock item 5", 3, 6466, 63.6);
        StockItemDTO s6 = new StockItemDTO("5875693", "Test stock item 6", 3, 6775, 25.99);
        StockItemDTO s7 = new StockItemDTO("4974659", "Test stock item 7", 4, 3456, 49.6);
        StockItemDTO s8 = new StockItemDTO("3085708", "Test stock item 8", 5, 8536, 25.99);
        StockItemDTO s9 = new StockItemDTO("9837058", "Test stock item 9", 5, 4674, 76.6);
        StockItemDTO s10 = new StockItemDTO("6387508", "Test stock item 10", 6, 5675, 25.99);

        Flux.just(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10)
                .flatMap(p -> stockItemService.insertStock(Mono.just(p))
                        .log(p.toString()))
                .subscribe();


    }
}

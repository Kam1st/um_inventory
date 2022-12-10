package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.StockItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DataSetupService implements CommandLineRunner {

    @Autowired
    StockItemService stockItemService;

    @Override
    public void run(String... args) throws Exception {
        StockItemDTO s1 = new StockItemDTO("2454544", "hi this is a test", 1, 3864, 28.6);
        StockItemDTO s2 = new StockItemDTO("7486504", "hi this is a test number 2", 1, 9736, 84.7);
        StockItemDTO s3 = new StockItemDTO("9735693", "hi this is a test number 3", 2, 7344, 39.6);

        Flux.just(s1, s2, s3)
                .flatMap(p -> stockItemService.insertStock(Mono.just(p))
                        .log(p.toString()))
                .subscribe();


    }
}

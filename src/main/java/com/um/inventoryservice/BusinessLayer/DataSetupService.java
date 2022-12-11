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

        StockItemDTO s1 = new StockItemDTO("2454544", "hi this is a test number 1", 1, 3864, 28.6);
        StockItemDTO s2 = new StockItemDTO("7486504", "hi this is a test number 2", 1, 9736, 84.7);
        StockItemDTO s3 = new StockItemDTO("9735693", "hi this is a test number 3", 2, 7344, 39.6);
        StockItemDTO s4 = new StockItemDTO("9736560", "hi this is a test number 4", 2, 3567, 35.6);
        StockItemDTO s5 = new StockItemDTO("9576508", "hi this is a test number 5", 3, 6466, 63.6);
        StockItemDTO s6 = new StockItemDTO("5875693", "hi this is a test number 6", 3, 6775, 74.6);
        StockItemDTO s7 = new StockItemDTO("4974659", "hi this is a test number 7", 4, 3456, 49.6);
        StockItemDTO s8 = new StockItemDTO("3085708", "hi this is a test number 8", 5, 8536, 85.6);
        StockItemDTO s9 = new StockItemDTO("9837058", "hi this is a test number 9", 5, 4674, 76.6);
        StockItemDTO s10 = new StockItemDTO("6387508", "hi this is a test number 10", 6, 5675, 94.6);

        Flux.just(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10)
                .flatMap(p -> stockItemService.insertStock(Mono.just(p))
                        .log(p.toString()))
                .subscribe();


    }
}

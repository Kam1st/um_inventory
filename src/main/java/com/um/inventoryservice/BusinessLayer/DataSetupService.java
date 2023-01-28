package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    StockItemService stockItemService;

    @Autowired
    OrderService orderService;
    @Autowired
    ClientService clientService;

    @Autowired
    EmployeeService employeeService;

    @Override
    public void run(String... args) throws Exception {

        OrderDTO o1 = new OrderDTO("1", new ArrayList<>());
        OrderDTO o2 = new OrderDTO("2", new ArrayList<>());
        OrderDTO o3 = new OrderDTO("2", new ArrayList<>());
        StockOrderDTO so1 = new StockOrderDTO("2454544", "this is the test for stock item 1", 6);
        StockOrderDTO so2 = new StockOrderDTO("7486504", "this is the test for stock item 2", 7);
        StockOrderDTO so3 = new StockOrderDTO("9735693", "this is the test for stock item 3", 8);
        StockOrderDTO so4 = new StockOrderDTO("7486504", "this is the test for stock item 3 x2", 3);
        o1.getStockOrderDTOS().add(so1);
        o2.getStockOrderDTOS().add(so2);
        o3.getStockOrderDTOS().add(so3);
        o3.getStockOrderDTOS().add(so4);

        Flux.just(o1, o2, o3)
                .flatMap(p -> orderService.insertOrder(Mono.just(p))
                        .log(p.toString()))
                .subscribe();


        StockItemDTO s1 = new StockItemDTO("2454544", "Test stock item 1", "Paul", 3864, 28.6, 28.6, 9);
        StockItemDTO s2 = new StockItemDTO("7486504", "Test stock item 2", "Paul", 9736, 25.99, 25.99, 5);
        StockItemDTO s3 = new StockItemDTO("9735693", "Test stock item 3", "Paul", 7344, 39.6, 39.6, 85);
        StockItemDTO s4 = new StockItemDTO("9736560", "Test stock item 4", "Marty", 3567, 25.99, 25.99, 9865);
        StockItemDTO s5 = new StockItemDTO("9576508", "Test stock item 5", "Paul", 6466, 63.6, 63.6, 64);
        StockItemDTO s6 = new StockItemDTO("5875693", "Test stock item 6", "Marty", 6775, 25.99, 25.99, 85);
        StockItemDTO s7 = new StockItemDTO("4974659", "Test stock item 7", "Paul", 3456, 49.6, 49.6, 85);
        StockItemDTO s8 = new StockItemDTO("3085708", "Test stock item 8", "Paul", 8536, 25.99, 25.99, 73);
        StockItemDTO s9 = new StockItemDTO("9837058", "Test stock item 9", "Bob", 4674, 76.6, 76.6, 96);
        StockItemDTO s10 = new StockItemDTO("6387508", "Test stock item 10", "Bob", 5675, 25.99, 25.99, 74);

        Flux.just(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10)
                .flatMap(p -> stockItemService.insertStock(Mono.just(p))
                        .log(p.toString()))
                .subscribe();


        ClientDTO c1 = new ClientDTO("Bob Ross","John Doe","Somewhere","1234567890");
        ClientDTO c2 = new ClientDTO("Bob Ross's brother","John Doe","Somewhere","1234567891");
        ClientDTO c3 = new ClientDTO("Bob Ross's sister","John Doe","Somewhere","1234567890");
        ClientDTO c4 = new ClientDTO("Bob Ross's father","John Doe","Somewhere","1234567891");
        ClientDTO c5 = new ClientDTO("Bob Ross's mother","John Doe","Somewhere","1234567890");
        ClientDTO c6 = new ClientDTO("Bob Ross's other brother","John Doe","Somewhere","1234567891");
        ClientDTO c7 = new ClientDTO("Bob Ross's uncle","John Doe","Somewhere","1234567890");
        ClientDTO c8 = new ClientDTO("Bob Ross's aunt","John Doe","Somewhere","1234567891");
        ClientDTO c9 = new ClientDTO("Bob Ross's child","John Doe","Somewhere","1234567890");
        ClientDTO c10 = new ClientDTO("Bob Ross's clone","John Doe","Somewhere","1234567891");

        Flux.just(c1,c2,c3,c4,c5,c6,c7,c8,c9,c10)
                .flatMap(c -> clientService.createClient(Mono.just(c))
                        .log(c.toString()))
                .subscribe();

        EmployeeDTO e1 = new EmployeeDTO("Kam", "CEO", "25th of January 2022", "Active");
        EmployeeDTO e2 = new EmployeeDTO("Clem", "CEO 2", "26th of January 2022", "Inactive");
        EmployeeDTO e3 = new EmployeeDTO("Max", "CEO 3", "27th of January 2022", "Active");
        EmployeeDTO e4 = new EmployeeDTO("Duncan", "CEO 4", "28th of January 2022", "Inactive");
        EmployeeDTO e5 = new EmployeeDTO("Stace", "CEO 5", "29th of January 2022", "Active");
        EmployeeDTO e6 = new EmployeeDTO("Elric", "CEO 6", "30th of January 2022", "Active");
        EmployeeDTO e7 = new EmployeeDTO("Bob", "CEO 7", "31st of January 2022", "Inactive");
        EmployeeDTO e8 = new EmployeeDTO("Ross", "CEO 8", "22nd of January 2022", "Active");
        EmployeeDTO e9 = new EmployeeDTO("John", "CEO 9", "23rd of January 2022", "Inactive");
        EmployeeDTO e10 = new EmployeeDTO("Doe", "CEO 10", "24th of January 2022", "Active");


        Flux.just(e1,e2,e3,e4,e5,e6,e7,e8,e9,e10)
                .flatMap(e -> employeeService.insertEmployee(Mono.just(e))
                        .log(e.toString()))
                .subscribe();
    }
}

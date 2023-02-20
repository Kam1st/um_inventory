package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
/*
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

        OrderDTO o1 = new OrderDTO("CL0001", new ArrayList<>());
        OrderDTO o2 = new OrderDTO("CL0002", new ArrayList<>());
        OrderDTO o3 = new OrderDTO("CL0002", new ArrayList<>());
        OrderDTO o4 = new OrderDTO("CL0003", new ArrayList<>());
        OrderDTO o5 = new OrderDTO("CL0004", new ArrayList<>());
        OrderDTO o6 = new OrderDTO("CL0005", new ArrayList<>());
        OrderDTO o7 = new OrderDTO("CL0006", new ArrayList<>());
        OrderDTO o8 = new OrderDTO("CL0007", new ArrayList<>());
        OrderDTO o9 = new OrderDTO("CL0005", new ArrayList<>());
        OrderDTO o10 = new OrderDTO("CL0005", new ArrayList<>());
        StockOrderDTO so1 = new StockOrderDTO("2454544", "90 pcs Plumbing Washers ", 6);
        StockOrderDTO so2 = new StockOrderDTO("7486504", "Delta T55PR Faucet, Chrome", 7);
        StockOrderDTO so3 = new StockOrderDTO("9735693", "Delta T3991 Valve Trim, Chrome", 8);
        StockOrderDTO so4 = new StockOrderDTO("8300127", "Smythe Shower Hose, 5 ft, metal", 3);
        StockOrderDTO so5 = new StockOrderDTO("9576508", "Regal L5348 Sink, Ceramic",  15);
        StockOrderDTO so6 = new StockOrderDTO("5875693", "Regal TH449, Flush Valve", 25);
        StockOrderDTO so7 = new StockOrderDTO("4974659", "Bianco 4093, Double Sink, Stainless", 17);
        StockOrderDTO so8 = new StockOrderDTO("3085708", "Seville IH032 Filtration Unit", 36);
        StockOrderDTO so9 = new StockOrderDTO("9837058", "Growler RP556 Tub Spout, Chrome", 24);
        StockOrderDTO so10 = new StockOrderDTO("6387508", "Stevens EL88T Tankless Heater",  9);
        o1.getStockOrderDTOS().add(so1);
        o2.getStockOrderDTOS().add(so2);
        o3.getStockOrderDTOS().add(so3);
        o4.getStockOrderDTOS().add(so4);
        o5.getStockOrderDTOS().add(so5);
        o5.getStockOrderDTOS().add(so2);
        o6.getStockOrderDTOS().add(so6);
        o7.getStockOrderDTOS().add(so7);
        o8.getStockOrderDTOS().add(so8);
        o8.getStockOrderDTOS().add(so9);
        o9.getStockOrderDTOS().add(so10);
        o10.getStockOrderDTOS().add(so1);
        o9.getStockOrderDTOS().add(so7);
        o9.getStockOrderDTOS().add(so8);
        o6.getStockOrderDTOS().add(so4);
        o10.getStockOrderDTOS().add(so2);

        Flux.just(o1, o2, o3, o4, o5, o6, o7, o8, o9, o10)
                .flatMap(p -> orderService.insertOrder(Mono.just(p))
                        .log(p.toString()))
                .subscribe();


        StockItemDTO s1 = new StockItemDTO("2454544", "90 pcs Plumbing Washers ", "Wentwood & Bros.", 3864, 28.65, 32.95, 120);
        StockItemDTO s2 = new StockItemDTO("7486504", "Delta T55PR Faucet, Chrome", "Delta", 9736, 225.99, 253.99, 34);
        StockItemDTO s3 = new StockItemDTO("9735693", "Delta T3991 Valve Trim, Chrome", "Delta", 7344, 139.60, 155.95, 85);
        StockItemDTO s4 = new StockItemDTO("8300127", "Smythe Shower Hose, 5 ft, metal", "Smythe, Inc.", 3567, 14.99, 20.99, 365);
        StockItemDTO s5 = new StockItemDTO("9576508", "Regal L5348 Sink, Ceramic", "Regal National", 6466, 183.60, 243.85, 64);
        StockItemDTO s6 = new StockItemDTO("5875693", "Regal TH449, Flush Valve", "Regal National", 6775, 95.99, 115.99, 85);
        StockItemDTO s7 = new StockItemDTO("4974659", "Bianco 4093, Double Sink, Stainless", "Finelli Corp.", 3456, 549.25, 629.65, 85);
        StockItemDTO s8 = new StockItemDTO("3085708", "Seville IH032 Filtration Unit", "Seville", 8536, 1215.99, 1525.99, 73);
        StockItemDTO s9 = new StockItemDTO("9837058", "Growler RP556 Tub Spout, Chrome", "Growler", 4674, 76.65, 89.96, 176);
        StockItemDTO s10 = new StockItemDTO("6387508", "Stevens EL88T Tankless Heater", "Stevens International", 675, 2025.99, 2115.99, 74);

        Flux.just(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10)
                .flatMap(p -> stockItemService.insertStock(Mono.just(p))
                        .log(p.toString()))
                .subscribe();


        ClientDTO c1 = new ClientDTO("Merril Construction","Paul Merril","483 Oakland","8529347123");
        ClientDTO c2 = new ClientDTO("Hilton Inc.","Sara Powell","9378 Parker","2145647734");
        ClientDTO c3 = new ClientDTO("Plumbers Corps.","Thomas Quinn","40 Southwest Drive","9264513487");
        ClientDTO c4 = new ClientDTO("Express Online","E. Wong","331 Matriarch Park","7436629005");
        ClientDTO c5 = new ClientDTO("Walton's","Kristen Blanchard","7701 Terrence Blvd.","3179075562");
        ClientDTO c6 = new ClientDTO("Quick Solutions","Stephen George","856 South Hill","7768845511");
        ClientDTO c7 = new ClientDTO("Repairs By Day","Melissa Rioux","55-4500 Shawnessy","5823340987");
        ClientDTO c8 = new ClientDTO("Seaton Landscaping","Shawn Seaton","12 Patriot Way","6194937856");
        ClientDTO c9 = new ClientDTO("Bob Ross Foundation","John Ross","44th Avenue East","2189956783");
        ClientDTO c10 = new ClientDTO("Home Goods","M. L. Jenkins","183 Meadow Grove","5154667834");

        Flux.just(c1,c2,c3,c4,c5,c6,c7,c8,c9,c10)
                .flatMap(c -> clientService.createClient(Mono.just(c))
                        .log(c.toString()))
                .subscribe();

        EmployeeDTO e1 = new EmployeeDTO("Zahir Hatteea", "CEO", "January 25, 2012", "Active");
        EmployeeDTO e2 = new EmployeeDTO("Hal Cliffords", "Operations Manager", "May 19, 2013", "Inactive");
        EmployeeDTO e3 = new EmployeeDTO("Max Jones", "Sales Clerk", "April 7, 2021", "Active");
        EmployeeDTO e4 = new EmployeeDTO("Elisa Ruiz", "Senior Accounting Staff", "October 22, 2014", "Inactive");
        EmployeeDTO e5 = new EmployeeDTO("Claire Stein", "Warehouse Staff", "May 18, 2015", "Active");
        EmployeeDTO e6 = new EmployeeDTO("Eric Nguyen", "Shipping Staff", "30th of January 2022", "Active");
        EmployeeDTO e7 = new EmployeeDTO("Rob Kleiner", "Shipping Foreman", "November 12, 2018", "Inactive");
        EmployeeDTO e8 = new EmployeeDTO("Sheryl Ross", "VP of Finance", "March 16, 2021", "Active");
        EmployeeDTO e9 = new EmployeeDTO("John Prest", "Sales Supervisor", "April 24, 2017", "Inactive");
        EmployeeDTO e10 = new EmployeeDTO("Darryl Patel", "Inventory Manager", "February 2, 2020", "Active");


        Flux.just(e1,e2,e3,e4,e5,e6,e7,e8,e9,e10)
                .flatMap(e -> employeeService.insertEmployee(Mono.just(e))
                        .log(e.toString()))
                .subscribe();
    }
}

*/
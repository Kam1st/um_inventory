package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.EmployeeDTO;
import com.um.inventoryservice.DataLayer.OrderDTO;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    Mono<EmployeeDTO> insertEmployee(Mono<EmployeeDTO> employeeDTOMono);
}

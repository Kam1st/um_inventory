package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.EmployeeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    Flux<EmployeeDTO> getAll();
    Mono<EmployeeDTO> insertEmployee(Mono<EmployeeDTO> employeeDTOMono);
    Mono<EmployeeDTO> getEmployeeById(String employeeId);
    Mono<EmployeeDTO> updateEmployee(String employeeId, Mono<EmployeeDTO> employeeDTOMono);
    Mono<Void> deleteEmployeeById(String employeeId);
}

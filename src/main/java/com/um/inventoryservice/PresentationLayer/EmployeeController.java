package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.BusinessLayer.EmployeeService;
import com.um.inventoryservice.DataLayer.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public Mono<EmployeeDTO> insertEmployee(@RequestBody Mono<EmployeeDTO> employeeDTOMono) {
        return employeeService.insertEmployee(employeeDTOMono);
    }
}


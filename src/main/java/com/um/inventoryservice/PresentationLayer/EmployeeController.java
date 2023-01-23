package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.BusinessLayer.EmployeeService;
import com.um.inventoryservice.DataLayer.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public Flux<EmployeeDTO> getAllEmployees() {
        return employeeService.getAll();
    }
    @PostMapping
    public Mono<EmployeeDTO> insertEmployee(@RequestBody Mono<EmployeeDTO> employeeDTOMono) {
        return employeeService.insertEmployee(employeeDTOMono);
    }

    @GetMapping("{employeeId}")
    public Mono<ResponseEntity<EmployeeDTO>> getEmployeeById(@PathVariable String employeeId) {
        return employeeService
                .getEmployeeById(employeeId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("{employeeId}")
    public Mono<ResponseEntity<EmployeeDTO>> updateEmployee(@PathVariable String employeeId, @RequestBody Mono<EmployeeDTO> employeeDTOMono){
      return employeeService.updateEmployee(employeeId, employeeDTOMono)
              .map(ResponseEntity::ok)
              .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}


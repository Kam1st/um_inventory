package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    EmployeeService employeeService;

    @MockBean
    EmployeeRepository employeeRepository;

    Employee employee = buildEmployee();

    EmployeeDTO employeeDTO = buildEmployeeDTO();

    @Test
    void getAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(Flux.just(employee));

        Flux<EmployeeDTO> employeeDTOF = employeeService.getAll();

        StepVerifier
                .create(employeeDTOF)
                .consumeNextWith(foundEmployee ->{
                    assertEquals(employee.getEmployeeId(), foundEmployee.getEmployeeId());
                    assertEquals(employee.getEmployeeName(), foundEmployee.getEmployeeName());
                    assertEquals(employee.getPosition(), foundEmployee.getPosition());
                    assertEquals(employee.getDateOfHire(), foundEmployee.getDateOfHire());
                    assertEquals(employee.getStatus(), foundEmployee.getStatus());
                })
                .verifyComplete();
    }

    @Test
    void insertEmployee() {

        employeeService.insertEmployee(Mono.just(employeeDTO))
                .map(employeeDTO1 -> {
                    assertEquals(employeeDTO1.getEmployeeId(), employee.getEmployeeId());
                    assertEquals(employeeDTO1.getEmployeeName(), employee.getEmployeeName());
                    assertEquals(employeeDTO1.getPosition(), employee.getPosition());
                    assertEquals(employeeDTO1.getDateOfHire(), employee.getDateOfHire());
                    assertEquals(employeeDTO1.getStatus(), employee.getStatus());
                    return employeeDTO1;
                });
    }



    private Employee buildEmployee() {
        return Employee.builder()
                .employeeId("1")
                .employeeName("Kam")
                .position("CEO")
                .dateOfHire("25th of January 2022")
                .status("Active")
               .build();
    }

    private EmployeeDTO buildEmployeeDTO() {
        return EmployeeDTO.builder()
                .employeeId("1")
                .employeeName("Kam")
                .position("CEO")
                .dateOfHire("25th of January 2022")
                .status("Active")
                .build();
    }

}
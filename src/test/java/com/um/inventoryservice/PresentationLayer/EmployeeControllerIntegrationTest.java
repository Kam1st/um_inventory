package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.DataLayer.*;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
class EmployeeControllerIntegrationTest {

    private final Employee employee = buildEmployee();

    private final EmployeeDTO employeeDTO = buildEmployeeDTO();
    String EMPLOYEE_ID = employeeDTO.getEmployeeId();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void getAllEmployees() {
        Publisher<Employee> setup = employeeRepository.deleteAll().thenMany(employeeRepository.save(employee));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].employeeId").isEqualTo(employee.getEmployeeId())
                .jsonPath("$[0].employeeName").isEqualTo(employee.getEmployeeName())
                .jsonPath("$[0].position").isEqualTo(employee.getPosition())
                .jsonPath("$[0].dateOfHire").isEqualTo(employee.getDateOfHire())
                .jsonPath("$[0].status").isEqualTo(employee.getStatus());

    }

    @Test
    void insertEmployee() {
        Publisher<Void> setup = employeeRepository.deleteAll();

        StepVerifier
                .create(setup)
                .expectNextCount(0)
                .verifyComplete();

        webTestClient
                .post()
                .uri("/employees")
                .body(Mono.just(employee), EmployeeDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(EmployeeDTO.class)
                .value((dto) -> {
                    assertEquals(dto.getEmployeeId(), employee.getEmployeeId());
                    assertEquals(dto.getEmployeeName(), employee.getEmployeeName());
                    assertEquals(dto.getPosition(), employee.getPosition());
                    assertEquals(dto.getDateOfHire(), employee.getDateOfHire());
                    assertEquals(dto.getStatus(), employee.getStatus());
                });
    }

    @Test
    void getEmployeeById() {
        Publisher<Employee> setup = employeeRepository.deleteAll().thenMany(employeeRepository.save(employee));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/employees/" + EMPLOYEE_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.employeeId").isEqualTo(employeeDTO.getEmployeeId())
                .jsonPath("$.employeeName").isEqualTo(employeeDTO.getEmployeeName())
                .jsonPath("$.position").isEqualTo(employeeDTO.getPosition())
                .jsonPath("$.dateOfHire").isEqualTo(employeeDTO.getDateOfHire())
                .jsonPath("$.status").isEqualTo(employeeDTO.getStatus());

    }

    @Test
    void updateEmployee() {
        Publisher<Employee> setup = employeeRepository.deleteAll().thenMany(employeeRepository.save(employee));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .put()
                .uri("/employees/" + EMPLOYEE_ID)
                .body(Mono.just(employeeDTO), EmployeeDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.employeeId").isEqualTo(employeeDTO.getEmployeeId())
                .jsonPath("$.employeeName").isEqualTo(employeeDTO.getEmployeeName())
                .jsonPath("$.position").isEqualTo(employeeDTO.getPosition())
                .jsonPath("$.dateOfHire").isEqualTo(employeeDTO.getDateOfHire())
                .jsonPath("$.status").isEqualTo(employeeDTO.getStatus());
    }

    @Test
    void deleteEmployee() {
        Publisher<Employee> setup = employeeRepository.deleteAll().thenMany(employeeRepository.save(employee));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .delete()
                .uri("/employees/" + EMPLOYEE_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }
    @Test
    void toStringBuildersEmployee() {
        System.out.println(Employee.builder());
        System.out.println(EmployeeDTO.builder());
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
package com.um.inventoryservice.DataLayer;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    private String id;

    private String employeeId;
    private String employeeName;
    private String position;
    private String dateOfHire;
    private String status;
}
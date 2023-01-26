package com.um.inventoryservice.DataLayer;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private String employeeId;
    private String employeeName;
    private String position;
    private String dateOfHire;
    private String status;

    public EmployeeDTO(String employeeName, String position, String dateOfHire, String status) {
        this.employeeName = employeeName;
        this.position = position;
        this.dateOfHire = dateOfHire;
        this.status = status;
    }
}

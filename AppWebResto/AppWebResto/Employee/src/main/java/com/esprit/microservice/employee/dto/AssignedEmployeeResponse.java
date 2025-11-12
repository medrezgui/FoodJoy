package com.esprit.microservice.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignedEmployeeResponse {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String role;


}

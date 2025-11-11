package com.esprit.microservice.employee.EntityEmployee;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private double salary;
    private String role;
    private boolean available = true;
    private int assignmentsCount = 0;

	// Reservation linkage


}
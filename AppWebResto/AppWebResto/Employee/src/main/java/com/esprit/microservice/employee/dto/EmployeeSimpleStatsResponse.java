package com.esprit.microservice.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

    @Data
    @AllArgsConstructor
    public class EmployeeSimpleStatsResponse {
        private double occupationRate;
        private double averageAssignments;
        private long totalAssignments;
    }



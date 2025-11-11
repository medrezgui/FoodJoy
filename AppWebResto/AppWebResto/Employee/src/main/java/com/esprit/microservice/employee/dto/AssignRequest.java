package com.esprit.microservice.employee.dto;

import lombok.Data;

@Data
public class AssignRequest {
    private Long reservationId;   // ID de la réservation
    private String role;          // le rôle demandé : Serveur
    private Long tableId;         // optionnel pour assignation table
}

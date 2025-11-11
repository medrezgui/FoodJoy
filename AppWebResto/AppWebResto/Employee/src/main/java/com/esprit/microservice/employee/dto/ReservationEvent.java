package com.esprit.microservice.employee.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ReservationEvent {
    private String eventType;
    private Long reservationId;
    private String clientEmail;
    private String clientName;
    private LocalDateTime reservationDate;
    private Integer numberOfPeople;
    private String status;

    // Getters + setters
}

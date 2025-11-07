// ReservationEvent.java
package com.esprit.ms.reservationms.dto;

import com.esprit.ms.reservationms.model.Reservation;
import java.time.LocalDateTime;

public class ReservationEvent {
    private String eventType; // CREATED, UPDATED, CANCELLED
    private Long reservationId;
    private String clientEmail;
    private String clientName;
    private LocalDateTime reservationDate;
    private Integer numberOfPeople;
    private String status;
    private LocalDateTime eventTimestamp;

    // Constructors
    public ReservationEvent() {
        this.eventTimestamp = LocalDateTime.now();
    }

    public ReservationEvent(String eventType, Reservation reservation) {
        this();
        this.eventType = eventType;
        this.reservationId = reservation.getId();
        this.clientEmail = reservation.getClientEmail();
        this.clientName = reservation.getClientNom();
        this.reservationDate = reservation.getDateReservation();
        this.numberOfPeople = reservation.getNombrePersonnes();
        this.status = reservation.getStatut().name();
    }

    // Getters and Setters
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

    public String getClientEmail() { return clientEmail; }
    public void setClientEmail(String clientEmail) { this.clientEmail = clientEmail; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public LocalDateTime getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDateTime reservationDate) { this.reservationDate = reservationDate; }

    public Integer getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(Integer numberOfPeople) { this.numberOfPeople = numberOfPeople; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getEventTimestamp() { return eventTimestamp; }
    public void setEventTimestamp(LocalDateTime eventTimestamp) { this.eventTimestamp = eventTimestamp; }
}
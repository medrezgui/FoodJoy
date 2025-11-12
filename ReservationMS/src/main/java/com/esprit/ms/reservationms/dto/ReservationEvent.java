package com.esprit.ms.reservationms.dto;

import com.esprit.ms.reservationms.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEvent {
    private String eventType; // CREATED, UPDATED, CANCELLED
    private Long reservationId;
    private String clientEmail;
    private String clientName;
    private Long tableId;
    private LocalDateTime reservationDate;
    private Integer numberOfPeople;
    private String status;
    private String commentaires;
    @Builder.Default
    private LocalDateTime eventTimestamp = LocalDateTime.now();
    // Custom constructor for convenience
    public ReservationEvent(String eventType, Reservation reservation) {
        this.eventType = eventType;
        this.reservationId = reservation.getId();
        this.clientEmail = reservation.getClientEmail();
        this.clientName = reservation.getClientNom();
        this.reservationDate = reservation.getDateReservation();
        this.numberOfPeople = reservation.getNombrePersonnes();
        this.status = reservation.getStatut().name();
        this.tableId = reservation.getTableId();
        this.commentaires = reservation.getCommentaires();
    }
}
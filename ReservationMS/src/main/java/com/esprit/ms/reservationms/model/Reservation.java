package com.esprit.ms.reservationms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_nom", nullable = false)
    private String clientNom;

    @Column(name = "client_email", nullable = false)
    private String clientEmail;

    @Column(name = "client_telephone")
    private String clientTelephone;

    @Column(name = "date_reservation", nullable = false)
    private LocalDateTime dateReservation;

    @Column(name = "nombre_personnes", nullable = false)
    private Integer nombrePersonnes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutReservation statut = StatutReservation.CONFIRMEE;

    private String commentaires;

    @Column(name = "table_id")
    private Long tableId;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();

    public enum StatutReservation {
        CONFIRMEE, ANNULEE, TERMINEE, EN_ATTENTE
    }

    // Fixed constructor - now properly uses all parameters
    public Reservation(String clientNom, String clientEmail, String clientTelephone,
                       LocalDateTime dateReservation, Integer nombrePersonnes, Long tableId,
                       StatutReservation statut, String commentaires) {
        this.clientNom = clientNom;
        this.clientEmail = clientEmail;
        this.clientTelephone = clientTelephone;
        this.dateReservation = dateReservation;
        this.nombrePersonnes = nombrePersonnes;
        this.tableId = tableId;
        this.statut = statut != null ? statut : StatutReservation.CONFIRMEE;
        this.commentaires = commentaires != null ? commentaires : "";
        this.dateCreation = LocalDateTime.now();
    }
}
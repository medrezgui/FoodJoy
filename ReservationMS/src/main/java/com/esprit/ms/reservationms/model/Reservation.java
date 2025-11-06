package com.esprit.ms.reservationms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
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

    // Constructeurs
    public Reservation() {}

    public Reservation(String clientNom, String clientEmail, String clientTelephone,
                       LocalDateTime dateReservation, Integer nombrePersonnes) {
        this.clientNom = clientNom;
        this.clientEmail = clientEmail;
        this.clientTelephone = clientTelephone;
        this.dateReservation = dateReservation;
        this.nombrePersonnes = nombrePersonnes;
    }

    // Getters et Setters
    public Long getId() { return id; }

    public String getClientNom() { return clientNom; }
    public void setClientNom(String clientNom) { this.clientNom = clientNom; }

    public String getClientEmail() { return clientEmail; }
    public void setClientEmail(String clientEmail) { this.clientEmail = clientEmail; }

    public String getClientTelephone() { return clientTelephone; }
    public void setClientTelephone(String clientTelephone) { this.clientTelephone = clientTelephone; }

    public LocalDateTime getDateReservation() { return dateReservation; }
    public void setDateReservation(LocalDateTime dateReservation) { this.dateReservation = dateReservation; }

    public Integer getNombrePersonnes() { return nombrePersonnes; }
    public void setNombrePersonnes(Integer nombrePersonnes) { this.nombrePersonnes = nombrePersonnes; }

    public StatutReservation getStatut() { return statut; }
    public void setStatut(StatutReservation statut) { this.statut = statut; }

    public String getCommentaires() { return commentaires; }
    public void setCommentaires(String commentaires) { this.commentaires = commentaires; }

    public Long getTableId() { return tableId; }
    public void setTableId(Long tableId) { this.tableId = tableId; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}
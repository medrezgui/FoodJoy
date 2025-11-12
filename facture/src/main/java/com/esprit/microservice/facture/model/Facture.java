package com.esprit.microservice.facture.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroFacture;

    private Long commandeId; // FK vers Commande, assume Commande entity exists elsewhere

    private Double montantTotal;

    private LocalDateTime dateCreation;

    private String methodePaiement; // ex: "Carte", "Espèces"

    private String statutPaiement; // ex: "PAYÉ", "EN_ATTENTE"
}
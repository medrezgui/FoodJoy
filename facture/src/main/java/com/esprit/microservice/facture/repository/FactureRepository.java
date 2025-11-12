package com.esprit.microservice.facture.repository;


import com.esprit.microservice.facture.model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    // Recherche par statut de paiement
    List<Facture> findByStatutPaiement(String statutPaiement);

    // Recherche par méthode de paiement
    List<Facture> findByMethodePaiement(String methodePaiement);

    // Recherche par période de création
    List<Facture> findByDateCreationBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Recherche par numéro de facture (unique)
    Facture findByNumeroFacture(String numeroFacture);
}
package com.esprit.microservice.facture.service;

import com.esprit.microservice.facture.model.Facture;

import java.time.LocalDateTime;
import java.util.List;

public interface IFactureService {
    Facture createFacture(Facture facture);

    Facture getFactureById(Long id);

    List<Facture> getAllFactures();

    Facture updateFacture(Long id, Facture facture);

    void deleteFacture(Long id);

    List<Facture> findFacturesByStatutPaiement(String statutPaiement);

    List<Facture> findFacturesByMethodePaiement(String methodePaiement);

    List<Facture> findFacturesByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    Facture findFactureByNumeroFacture(String numeroFacture);
}

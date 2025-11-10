package com.esprit.microservice.facture.service;

import com.esprit.microservice.facture.model.Facture;
import com.esprit.microservice.facture.repository.FactureRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FactureService implements IFactureService{
    @Autowired
    private FactureRepository factureRepository;

    @Override
    public Facture createFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    @Override
    public Facture getFactureById(Long id) {
        return factureRepository.findById(id).orElseThrow(() -> new RuntimeException("Facture not found"));
    }

    @Override
    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    @Override
    public Facture updateFacture(Long id, Facture facture) {
        Facture existing = getFactureById(id);
        existing.setNumeroFacture(facture.getNumeroFacture());
        existing.setCommandeId(facture.getCommandeId());
        existing.setMontantTotal(facture.getMontantTotal());
        existing.setDateCreation(facture.getDateCreation());
        existing.setMethodePaiement(facture.getMethodePaiement());
        existing.setStatutPaiement(facture.getStatutPaiement());
        return factureRepository.save(existing);
    }

    @Override
    public void deleteFacture(Long id) {
        factureRepository.deleteById(id);
    }

    // Implémentation des méthodes avancées
    @Override
    public List<Facture> findFacturesByStatutPaiement(String statutPaiement) {
        return factureRepository.findByStatutPaiement(statutPaiement);
    }

    @Override
    public List<Facture> findFacturesByMethodePaiement(String methodePaiement) {
        return factureRepository.findByMethodePaiement(methodePaiement);
    }

    @Override
    public List<Facture> findFacturesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return factureRepository.findByDateCreationBetween(startDate, endDate);
    }

    @Override
    public Facture findFactureByNumeroFacture(String numeroFacture) {
        return factureRepository.findByNumeroFacture(numeroFacture);
    }
}

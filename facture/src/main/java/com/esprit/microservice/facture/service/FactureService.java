/*package com.esprit.microservice.facture.service;

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
*/
package com.esprit.microservice.facture.service;

import com.esprit.microservice.facture.model.Facture;
import com.esprit.microservice.facture.repository.FactureRepository;
import com.esprit.microservice.facture.dto.FactureDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FactureService implements IFactureService {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private FactureProducer factureProducer;

    private static final Logger log = LoggerFactory.getLogger(FactureService.class);

    /**
     * Sauvegarde d’une facture en base de données et envoi d’un DTO à RabbitMQ.
     * Si la sauvegarde réussit mais que l’envoi échoue, la facture reste persistée.
     */
    @Override
    @Transactional
    public Facture createFacture(Facture facture) {
        // Sauvegarde en base
        Facture savedFacture = factureRepository.save(facture);
        log.info("✅ Facture sauvegardée : {}", savedFacture.getNumeroFacture());

        // Construction du DTO à envoyer
        FactureDTO factureDTO = new FactureDTO(
                savedFacture.getId(),
                savedFacture.getNumeroFacture(),
                savedFacture.getCommandeId(),
                savedFacture.getMontantTotal(),
                savedFacture.getDateCreation(),
                savedFacture.getMethodePaiement(),
                savedFacture.getStatutPaiement()
        );

        // Envoi asynchrone via RabbitMQ
        try {
            factureProducer.sendFacture(factureDTO);
            log.info("📤 Facture envoyée à RabbitMQ : {}", factureDTO.getNumeroFacture());
        } catch (Exception e) {
            log.error("⚠️ Échec d’envoi du message RabbitMQ pour la facture {}", factureDTO.getNumeroFacture(), e);
        }

        return savedFacture;
    }

    @Override
    public Facture getFactureById(Long id) {
        return factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture not found"));
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

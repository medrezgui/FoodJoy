/*package com.esprit.microservice.facture.controller;


import com.esprit.microservice.facture.model.Facture;
import com.esprit.microservice.facture.service.IFactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/factures")
public class FactureController {

    @Autowired
    private IFactureService factureService;

    @PostMapping
    public ResponseEntity<Facture> createFacture(@RequestBody Facture facture) {
        return ResponseEntity.ok(factureService.createFacture(facture));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Long id) {
        return ResponseEntity.ok(factureService.getFactureById(id));
    }

    @GetMapping
    public ResponseEntity<List<Facture>> getAllFactures() {
        return ResponseEntity.ok(factureService.getAllFactures());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Facture> updateFacture(@PathVariable Long id, @RequestBody Facture facture) {
        return ResponseEntity.ok(factureService.updateFacture(id, facture));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
        return ResponseEntity.noContent().build();
    }
    // Endpoints pour les méthodes avancées
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Facture>> getFacturesByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(factureService.findFacturesByStatutPaiement(statut));
    }

    @GetMapping("/methode/{methode}")
    public ResponseEntity<List<Facture>> getFacturesByMethode(@PathVariable String methode) {
        return ResponseEntity.ok(factureService.findFacturesByMethodePaiement(methode));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Facture>> getFacturesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(factureService.findFacturesByDateRange(startDate, endDate));
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<Facture> getFactureByNumero(@PathVariable String numero) {
        return ResponseEntity.ok(factureService.findFactureByNumeroFacture(numero));
    }
}*/
package com.esprit.microservice.facture.controller;

import com.esprit.microservice.facture.model.Facture;
import com.esprit.microservice.facture.service.IFactureService;
import com.esprit.microservice.facture.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/factures")
public class FactureController {

    @Autowired
    private IFactureService factureService;

    private final String hello = "Hello, I'm the Facture MS 👋";

    @RequestMapping("/helloF")
    public String sayHello() {
        return hello;
    }

    // ✅ Nouveau endpoint pour création + envoi RabbitMQ
    @PostMapping("/send")
    public ResponseEntity<Facture> createFactureAndSend(@RequestBody Facture facture) {
        Facture savedFacture = factureService.createFacture(facture);
        return ResponseEntity.ok(savedFacture);
    }

    // === Endpoints existants ===

    @PostMapping
    public ResponseEntity<Facture> createFacture(@RequestBody Facture facture) {
        return ResponseEntity.ok(factureService.createFacture(facture));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Long id) {
        return ResponseEntity.ok(factureService.getFactureById(id));
    }

    @GetMapping
    public ResponseEntity<List<Facture>> getAllFactures() {
        return ResponseEntity.ok(factureService.getAllFactures());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Facture> updateFacture(@PathVariable Long id, @RequestBody Facture facture) {
        return ResponseEntity.ok(factureService.updateFacture(id, facture));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
        return ResponseEntity.noContent().build();
    }

    // --- Méthodes avancées ---
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Facture>> getFacturesByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(factureService.findFacturesByStatutPaiement(statut));
    }

    @GetMapping("/methode/{methode}")
    public ResponseEntity<List<Facture>> getFacturesByMethode(@PathVariable String methode) {
        return ResponseEntity.ok(factureService.findFacturesByMethodePaiement(methode));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Facture>> getFacturesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(factureService.findFacturesByDateRange(startDate, endDate));
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<Facture> getFactureByNumero(@PathVariable String numero) {
        return ResponseEntity.ok(factureService.findFactureByNumeroFacture(numero));
    }

    // Endpoint complémentaire pour récupérer par ID façon atelier
    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<Facture>> getFactureOptionalById(@PathVariable Long id) {
        return ResponseEntity.ok(Optional.ofNullable(factureService.getFactureById(id)));
    }
}


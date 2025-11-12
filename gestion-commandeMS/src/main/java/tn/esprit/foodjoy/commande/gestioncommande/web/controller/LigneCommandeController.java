package tn.esprit.foodjoy.commande.gestioncommande.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foodjoy.commande.gestioncommande.application.dto.LigneCommandeDTO;
import tn.esprit.foodjoy.commande.gestioncommande.application.service.LigneCommandeService;

import java.util.List;

@RestController
@RequestMapping("/api/lignes-commande")
@CrossOrigin(origins = "*")
public class LigneCommandeController {

    private final LigneCommandeService ligneCommandeService;

    public LigneCommandeController(LigneCommandeService ligneCommandeService) {
        this.ligneCommandeService = ligneCommandeService;
    }

    // CRUD Operations

    @PostMapping("/commande/{commandeId}")
    public ResponseEntity<LigneCommandeDTO> creerLigneCommande(
            @PathVariable Long commandeId,
            @Valid @RequestBody LigneCommandeDTO ligneCommandeDTO) {
        LigneCommandeDTO createdLigne = ligneCommandeService.creerLigneCommande(commandeId, ligneCommandeDTO);
        return new ResponseEntity<>(createdLigne, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LigneCommandeDTO> obtenirLigneCommandeParId(@PathVariable Long id) {
        LigneCommandeDTO ligneCommandeDTO = ligneCommandeService.trouverLigneCommandeParId(id);
        return ResponseEntity.ok(ligneCommandeDTO);
    }

    @GetMapping
    public ResponseEntity<List<LigneCommandeDTO>> obtenirToutesLesLignesCommande() {
        List<LigneCommandeDTO> lignes = ligneCommandeService.trouverToutesLesLignesCommande();
        return ResponseEntity.ok(lignes);
    }

    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<LigneCommandeDTO>> obtenirLignesCommandeParCommandeId(
            @PathVariable Long commandeId) {
        List<LigneCommandeDTO> lignes = ligneCommandeService.trouverLignesCommandeParCommandeId(commandeId);
        return ResponseEntity.ok(lignes);
    }

    @GetMapping("/plat/{platId}")
    public ResponseEntity<List<LigneCommandeDTO>> obtenirLignesCommandeParPlatId(
            @PathVariable Long platId) {
        List<LigneCommandeDTO> lignes = ligneCommandeService.trouverLignesCommandeParPlatId(platId);
        return ResponseEntity.ok(lignes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LigneCommandeDTO> mettreAJourLigneCommande(
            @PathVariable Long id,
            @Valid @RequestBody LigneCommandeDTO ligneCommandeDTO) {
        LigneCommandeDTO updatedLigne = ligneCommandeService.mettreAJourLigneCommande(id, ligneCommandeDTO);
        return ResponseEntity.ok(updatedLigne);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerLigneCommande(@PathVariable Long id) {
        ligneCommandeService.supprimerLigneCommande(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/commande/{commandeId}")
    public ResponseEntity<Void> supprimerToutesLesLignesCommandeParCommandeId(
            @PathVariable Long commandeId) {
        ligneCommandeService.supprimerToutesLesLignesCommandeParCommandeId(commandeId);
        return ResponseEntity.noContent().build();
    }
}


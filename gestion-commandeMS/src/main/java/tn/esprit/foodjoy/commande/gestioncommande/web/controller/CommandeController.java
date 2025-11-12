package tn.esprit.foodjoy.commande.gestioncommande.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foodjoy.commande.gestioncommande.application.dto.CommandeDTO;
import tn.esprit.foodjoy.commande.gestioncommande.application.dto.CommandeRequestDTO;
import tn.esprit.foodjoy.commande.gestioncommande.application.dto.LigneCommandeDTO;
import tn.esprit.foodjoy.commande.gestioncommande.application.dto.StatutUpdateDTO;
import tn.esprit.foodjoy.commande.gestioncommande.application.service.CommandeService;
import tn.esprit.foodjoy.commande.gestioncommande.application.service.LigneCommandeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "*")
public class CommandeController {

    private final CommandeService commandeService;
    private final LigneCommandeService ligneCommandeService;

    public CommandeController(CommandeService commandeService,
                             LigneCommandeService ligneCommandeService) {
        this.commandeService = commandeService;
        this.ligneCommandeService = ligneCommandeService;
    }

    // CRUD Operations

    @PostMapping
    public ResponseEntity<CommandeDTO> creerCommande(@Valid @RequestBody CommandeRequestDTO requestDTO) {
        CommandeDTO commandeDTO = commandeService.creerCommande(requestDTO);
        return new ResponseEntity<>(commandeDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeDTO> obtenirCommandeParId(@PathVariable Long id) {
        CommandeDTO commandeDTO = commandeService.trouverCommandeParId(id);
        return ResponseEntity.ok(commandeDTO);
    }

    @GetMapping("/numero/{numeroCommande}")
    public ResponseEntity<CommandeDTO> obtenirCommandeParNumero(@PathVariable String numeroCommande) {
        CommandeDTO commandeDTO = commandeService.trouverCommandeParNumero(numeroCommande);
        return ResponseEntity.ok(commandeDTO);
    }

    @GetMapping
    public ResponseEntity<List<CommandeDTO>> obtenirToutesLesCommandes() {
        List<CommandeDTO> commandes = commandeService.trouverToutesLesCommandes();
        return ResponseEntity.ok(commandes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommandeDTO> mettreAJourCommande(
            @PathVariable Long id,
            @Valid @RequestBody CommandeRequestDTO requestDTO) {
        CommandeDTO commandeDTO = commandeService.mettreAJourCommande(id, requestDTO);
        return ResponseEntity.ok(commandeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCommande(@PathVariable Long id) {
        commandeService.supprimerCommande(id);
        return ResponseEntity.noContent().build();
    }

    // Méthodes avancées

    @PatchMapping("/{id}/statut")
    public ResponseEntity<CommandeDTO> changerStatutCommande(
            @PathVariable Long id,
            @Valid @RequestBody StatutUpdateDTO statutUpdateDTO) {
        CommandeDTO commandeDTO = commandeService.changerStatutCommande(id, statutUpdateDTO);
        return ResponseEntity.ok(commandeDTO);
    }

    @GetMapping("/{id}/total")
    public ResponseEntity<Map<String, Object>> calculerTotalCommande(@PathVariable Long id) {
        Double total = commandeService.calculerTotalCommande(id);
        return ResponseEntity.ok(Map.of(
                "commandeId", id,
                "total", total
        ));
    }

    // Méthodes de recherche

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<CommandeDTO>> obtenirCommandesParStatut(@PathVariable String statut) {
        List<CommandeDTO> commandes = commandeService.trouverCommandesParStatut(statut);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<CommandeDTO>> obtenirCommandesParType(@PathVariable String type) {
        List<CommandeDTO> commandes = commandeService.trouverCommandesParType(type);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/employe/{employeId}")
    public ResponseEntity<List<CommandeDTO>> obtenirCommandesParEmploye(@PathVariable Long employeId) {
        List<CommandeDTO> commandes = commandeService.trouverCommandesParEmploye(employeId);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/table/{tableId}")
    public ResponseEntity<List<CommandeDTO>> obtenirCommandesParTable(@PathVariable Long tableId) {
        List<CommandeDTO> commandes = commandeService.trouverCommandesParTable(tableId);
        return ResponseEntity.ok(commandes);
    }

    // Gestion des lignes de commande pour une commande spécifique

    @GetMapping("/{commandeId}/lignes")
    public ResponseEntity<List<LigneCommandeDTO>> obtenirLignesCommandeParCommandeId(
            @PathVariable Long commandeId) {
        List<LigneCommandeDTO> lignes = ligneCommandeService.trouverLignesCommandeParCommandeId(commandeId);
        return ResponseEntity.ok(lignes);
    }

    @PostMapping("/{commandeId}/lignes")
    public ResponseEntity<LigneCommandeDTO> ajouterLigneCommande(
            @PathVariable Long commandeId,
            @Valid @RequestBody LigneCommandeDTO ligneCommandeDTO) {
        LigneCommandeDTO createdLigne = ligneCommandeService.creerLigneCommande(commandeId, ligneCommandeDTO);
        return new ResponseEntity<>(createdLigne, HttpStatus.CREATED);
    }

    @PutMapping("/{commandeId}/lignes/{ligneId}")
    public ResponseEntity<LigneCommandeDTO> mettreAJourLigneCommande(
            @PathVariable Long commandeId,
            @PathVariable Long ligneId,
            @Valid @RequestBody LigneCommandeDTO ligneCommandeDTO) {
        LigneCommandeDTO updatedLigne = ligneCommandeService.mettreAJourLigneCommande(ligneId, ligneCommandeDTO);
        return ResponseEntity.ok(updatedLigne);
    }

    @DeleteMapping("/{commandeId}/lignes/{ligneId}")
    public ResponseEntity<Void> supprimerLigneCommande(
            @PathVariable Long commandeId,
            @PathVariable Long ligneId) {
        ligneCommandeService.supprimerLigneCommande(ligneId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{commandeId}/lignes")
    public ResponseEntity<Void> supprimerToutesLesLignesCommande(
            @PathVariable Long commandeId) {
        ligneCommandeService.supprimerToutesLesLignesCommandeParCommandeId(commandeId);
        return ResponseEntity.noContent().build();
    }
}



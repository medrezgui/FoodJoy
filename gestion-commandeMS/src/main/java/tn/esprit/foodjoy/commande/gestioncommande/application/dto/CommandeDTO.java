package tn.esprit.foodjoy.commande.gestioncommande.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommandeDTO {

    private Long id;

    @NotBlank(message = "Le numéro de commande est obligatoire")
    private String numeroCommande;

    private String statut;

    private LocalDateTime dateCreation;

    @NotNull(message = "Le type de commande est obligatoire")
    private String typeCommande;

    private Long employeId;

    private Long tableId;

    @NotEmpty(message = "La commande doit contenir au moins une ligne de commande")
    @Valid
    private List<LigneCommandeDTO> lignesCommande = new ArrayList<>();

    private Double total;

    // Constructeurs
    public CommandeDTO() {
    }

    public CommandeDTO(String numeroCommande, String typeCommande, Long employeId, Long tableId) {
        this.numeroCommande = numeroCommande;
        this.typeCommande = typeCommande;
        this.employeId = employeId;
        this.tableId = tableId;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getTypeCommande() {
        return typeCommande;
    }

    public void setTypeCommande(String typeCommande) {
        this.typeCommande = typeCommande;
    }

    public Long getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public List<LigneCommandeDTO> getLignesCommande() {
        return lignesCommande;
    }

    public void setLignesCommande(List<LigneCommandeDTO> lignesCommande) {
        this.lignesCommande = lignesCommande;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}



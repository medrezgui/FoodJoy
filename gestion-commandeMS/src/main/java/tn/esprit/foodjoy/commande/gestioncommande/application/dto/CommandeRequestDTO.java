package tn.esprit.foodjoy.commande.gestioncommande.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CommandeRequestDTO {

    @NotNull(message = "Le type de commande est obligatoire")
    private String typeCommande;

    private Long employeId;

    private Long tableId;

    @NotEmpty(message = "La commande doit contenir au moins une ligne de commande")
    @Valid
    private List<LigneCommandeDTO> lignesCommande;

    // Constructeurs
    public CommandeRequestDTO() {
    }

    public CommandeRequestDTO(String typeCommande, Long employeId, Long tableId, List<LigneCommandeDTO> lignesCommande) {
        this.typeCommande = typeCommande;
        this.employeId = employeId;
        this.tableId = tableId;
        this.lignesCommande = lignesCommande;
    }

    // Getters et Setters
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
}



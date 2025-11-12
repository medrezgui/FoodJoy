package tn.esprit.foodjoy.commande.gestioncommande.application.dto;

import jakarta.validation.constraints.NotBlank;

public class StatutUpdateDTO {

    @NotBlank(message = "Le nouveau statut est obligatoire")
    private String nouveauStatut;

    // Constructeurs
    public StatutUpdateDTO() {
    }

    public StatutUpdateDTO(String nouveauStatut) {
        this.nouveauStatut = nouveauStatut;
    }

    // Getters et Setters
    public String getNouveauStatut() {
        return nouveauStatut;
    }

    public void setNouveauStatut(String nouveauStatut) {
        this.nouveauStatut = nouveauStatut;
    }
}



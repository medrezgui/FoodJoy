package tn.esprit.foodjoy.commande.gestioncommande.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

public class LigneCommandeDTO implements Serializable {

    private Long id;

    @NotNull(message = "L'ID du plat est obligatoire")
    private Long platId;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 1, message = "La quantité doit être supérieure à 0")
    private Integer quantite;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix unitaire doit être supérieur à 0")
    private Double prixUnitaire;

    private String commentaire;

    private Double sousTotal;

    // Constructeurs
    public LigneCommandeDTO() {
    }

    public LigneCommandeDTO(Long platId, Integer quantite, Double prixUnitaire, String commentaire) {
        this.platId = platId;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.commentaire = commentaire;
        this.sousTotal = quantite * prixUnitaire;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlatId() {
        return platId;
    }

    public void setPlatId(Long platId) {
        this.platId = platId;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Double getSousTotal() {
        if (sousTotal == null && quantite != null && prixUnitaire != null) {
            return quantite * prixUnitaire;
        }
        return sousTotal;
    }

    public void setSousTotal(Double sousTotal) {
        this.sousTotal = sousTotal;
    }
}


package tn.esprit.foodjoy.commande.gestioncommande.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entité Plat pour le cache local dans gestion-commandeMS
 * Les données sont synchronisées depuis Menu_PlatMS via RabbitMQ
 */
@Entity
@Table(name = "plat_cache")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plat {
    
    @Id
    @Column(name = "id_plat")
    private Long idPlat;

    @Column(name = "nom_plat", nullable = false)
    private String nomPlat;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double prix;

    private String categorie;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "est_disponible")
    private Boolean estDisponible;

    @Column(name = "menu_id")
    private Long menuId;
}


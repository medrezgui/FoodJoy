package tn.esprit.menu_plat.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlat;

    private String nomPlat;
    private String description;
    private Double prix;
    private String categorie;
    private String imageUrl;
    private Boolean estDisponible;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}

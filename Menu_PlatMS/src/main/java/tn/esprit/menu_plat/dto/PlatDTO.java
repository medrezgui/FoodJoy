package tn.esprit.menu_plat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatDTO implements Serializable {
    private Long idPlat;
    private String nomPlat;
    private String description;
    private Double prix;
    private String categorie;
    private String imageUrl;
    private Boolean estDisponible;
    private Long menuId;
}


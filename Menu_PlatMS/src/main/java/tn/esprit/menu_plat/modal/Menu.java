package tn.esprit.menu_plat.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMenu;

    private String nomMenu;
    private String description;
 //   private LocalDateTime dateCreation = LocalDateTime.now();
    private LocalDate dateCreation ;
    private Boolean estActif;
    private String categorie;
  @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    //@OneToMany(mappedBy = "menu", cascade = CascadeType.PERSIST)
    private List<Plat> plats;
}

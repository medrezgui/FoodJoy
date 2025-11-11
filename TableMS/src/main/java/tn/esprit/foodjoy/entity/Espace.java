package tn.esprit.foodjoy.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "espace")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Espace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    private String description;

    private Integer capaciteTotale;

    // Relation bidirectionnelle (optionnelle)
    @OneToMany(mappedBy = "espace", cascade = CascadeType.ALL)
    private List<TableResto> tables;
}

/*******/

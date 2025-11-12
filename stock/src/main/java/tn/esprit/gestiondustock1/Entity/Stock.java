package tn.esprit.gestiondustock1.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    private Double quantite;

    private LocalDateTime dateModification;

    private LocalDateTime datePeremption;
}


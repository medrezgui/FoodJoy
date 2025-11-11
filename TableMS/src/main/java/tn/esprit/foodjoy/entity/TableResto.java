package tn.esprit.foodjoy.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant_tables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableResto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tableNumber; // ex: "T01", "T12"

    @Column(nullable = false)
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "espace_id", nullable = false)
    private Espace espace;  // ex: "Terrasse", "Salle Principale", "VIP"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TableStatus status = TableStatus.FREE;

    private Long assignedServerId; // Référence à un employé (via microservice Personnel)

    // Pour futur plan interactif (optionnel)
    private Integer positionX;
    private Integer positionY;
}

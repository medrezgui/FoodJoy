package tn.esprit.gestiondustock1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactureDTO {
    private Long id;
    private String numeroFacture;
    private Long commandeId;
    private Double montantTotal;
    private LocalDateTime dateCreation;
    private String methodePaiement;
    private String statutPaiement;
}

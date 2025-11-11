package tn.esprit.foodjoy.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private Long id;
    private String clientNom;
    private String clientEmail;
    private String clientTelephone;
    private LocalDateTime dateReservation;
    private Integer nombrePersonnes;
    private String statut;
    private String commentaires;
    private Long tableId;
    private LocalDateTime dateCreation;
    //  enum in DTO class or separately
//    private StatutReservation statut;
//    public enum StatutReservation {
//        CONFIRMEE, ANNULEE, TERMINEE, EN_ATTENTE
//    }
}

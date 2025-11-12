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
    private Integer nombrePersonnes;
    private LocalDateTime dateReservation;
    private String statut;
    private Long tableId;

    //  enum in DTO class or separately
//    private StatutReservation statut;
//    public enum StatutReservation {
//        CONFIRMEE, ANNULEE, TERMINEE, EN_ATTENTE
//    }
}

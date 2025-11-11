// In your Table service - create this class
package tn.esprit.foodjoy.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationEvent {
    private String eventType;
   private Long reservationId;
    private String clientEmail;
    private String clientName;
    private Long tableId;
    private LocalDateTime reservationDate;
    private Integer numberOfPeople;
    private String status;  // This is what you need!
    private String comments;
    private LocalDateTime eventTimestamp;
}
package tn.esprit.foodjoy.dto;

import lombok.*;
import tn.esprit.foodjoy.entity.TableStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableDto {

    private Long id;
    private String tableNumber;
    private Integer capacity;
    private Long espaceId;
    private String espaceNom;
    private TableStatus status;
    private Long assignedServerId;
    private Integer positionX;
    private Integer positionY;
}
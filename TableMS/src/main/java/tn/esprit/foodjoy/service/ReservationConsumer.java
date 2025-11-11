package tn.esprit.foodjoy.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tn.esprit.foodjoy.config.RabbitMQConfig;
import tn.esprit.foodjoy.dto.ReservationDto;
import tn.esprit.foodjoy.dto.ReservationEvent;

@Service
public class ReservationConsumer {
    private final TableService tableService;
    private static final Logger log = LoggerFactory.getLogger(ReservationConsumer.class);


    public ReservationConsumer(TableService tableService,ObjectMapper objectMapper) {
        this.tableService = tableService;
    }
    // Ecoute la file TABLE_RESERV_QUEUE
    // Spring convertit automatiquement le JSON en ReservationDTO grâce au MessageConverter
//    @RabbitListener(queues = RabbitMQConfig.TABLE_RESERV_QUEUE, containerFactory =
//            "rabbitListenerContainerFactory")
//    public void receiveReservation(ReservationDto reservationDto) {
//        log.info("ReservationDTO reçu depuis RabbitMQ : {}", reservationDto);
//        // Déléguer la logique métier
//        tableService.receiveReservationStatus(reservationDto);
//    }

//    @RabbitListener(queues = RabbitMQConfig.TABLE_RESERV_QUEUE)
//    public void receiveReservation(ReservationEvent reservationEvent) {
//        log.info("ReservationEvent reçu: {}", reservationEvent);
//
//        // Convert to your existing ReservationDto format
//        ReservationDto reservationDto = ReservationDto.builder()
//                //.id(reservationEvent.getReservationId())
//                .tableId(reservationEvent.getTableId())
//                .statut(reservationEvent.getStatus())  // String status
//                .nombrePersonnes(reservationEvent.getNumberOfPeople())
//                .dateReservation(reservationEvent.getReservationDate())
//                .build();
//
//        // Use your existing method that expects ReservationDto
//        tableService.receiveReservationStatus(reservationEvent);
//    }

    @RabbitListener(queues = RabbitMQConfig.TABLE_RESERV_QUEUE)
    public void receiveReservation(ReservationEvent reservationEvent) {
        if (reservationEvent.getEventType() == null) {
            log.warn("Received reservation event with null eventType: {}", reservationEvent);
            return; // or handle appropriately
        }
        log.info("ReservationEvent reçu - Type: {}, Statut: {}, Table: {}",
                reservationEvent.getEventType(),
                reservationEvent.getStatus(),
                reservationEvent.getTableId());

        // Handle different event types
        switch (reservationEvent.getEventType()) {
            case "CREATED", "UPDATED", "CANCELLED" -> {
                // These affect table status
                //ReservationDto reservationDto = convertEventToDto(reservationEvent);
                tableService.receiveReservationStatus(reservationEvent);
            }
            case "DELETED" -> {
                // When reservation is deleted, free the table
                tableService.freeTable(reservationEvent.getTableId());
            }
            default -> log.warn("Type d'événement non géré: {}", reservationEvent.getEventType());
        }
    }
}

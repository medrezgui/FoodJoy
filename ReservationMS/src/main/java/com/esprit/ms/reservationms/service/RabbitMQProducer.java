// RabbitMQProducer.java
package com.esprit.ms.reservationms.service;

import com.esprit.ms.reservationms.config.RabbitMQConfig;
import com.esprit.ms.reservationms.dto.ReservationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger log = LoggerFactory.getLogger(RabbitMQProducer.class);

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendReservation(ReservationEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.RESERVATION_EXCHANGE,
                RabbitMQConfig.TABLE_RESERV_ROUTING_KEY, // Use the new routing key
                event
        );
        log.info("Reservation event sent to table service: {} for reservation: {}",
                event.getEventType(), event.getReservationId());
//        try {
//            rabbitTemplate.convertAndSend(RabbitMQConfig.TABLE_RESERV_QUEUE, event);
//            log.info("Reservation envoyée à la queue {} : {}", RabbitMQConfig.TABLE_RESERV_QUEUE, event);
//        } catch (AmqpException e) {
//            log.error("Erreur lors de l'envoi de la réservation à RabbitMQ", e);
//            throw e;
//        }
    }
    public void sendReservationEvent(ReservationEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.RESERVATION_EXCHANGE,
                RabbitMQConfig.RESERVATION_ROUTING_KEY,

                event
        );
        System.out.println("Reservation event sent: " + event.getEventType() + " for reservation: " + event.getReservationId());
    }

    public void sendNotification(ReservationEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.NOTIFICATION_EXCHANGE,
                RabbitMQConfig.NOTIFICATION_ROUTING_KEY,
                event
        );
        System.out.println("Notification sent for reservation: " + event.getReservationId());
    }
}
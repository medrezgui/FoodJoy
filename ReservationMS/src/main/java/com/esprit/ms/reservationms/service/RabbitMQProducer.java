// RabbitMQProducer.java
package com.esprit.ms.reservationms.service;

import com.esprit.ms.reservationms.config.RabbitMQConfig;
import com.esprit.ms.reservationms.dto.ReservationEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
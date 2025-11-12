package com.esprit.microservice.employee.rabbit;

import com.esprit.microservice.employee.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Envoie un événement vers une queue spécifique via un exchange et une routing key
     */
    public void sendEvent(Object event, String exchange, String routingKey) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
        System.out.println("✅ Event sent to exchange: " + exchange + " | routingKey: " + routingKey);
    }

    /**
     * Méthode pratique pour envoyer un événement de réservation
     */


    /**
     * Méthode pratique pour envoyer une notification
     */
    public void sendNotification(Object event) {
        sendEvent(event, RabbitMQConfig.NOTIFICATION_EXCHANGE, RabbitMQConfig.NOTIFICATION_ROUTING_KEY);
    }
}
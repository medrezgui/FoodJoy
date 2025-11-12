// RabbitMQConfig.java
package com.esprit.ms.reservationms.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Reservation Exchange and Queues
    public static final String RESERVATION_EXCHANGE = "reservation.exchange";
    public static final String RESERVATION_QUEUE = "reservation.queue";
    public static final String RESERVATION_ROUTING_KEY = "reservation.routingkey";
    public static final String TABLE_RESERV_QUEUE = "reservationQueue";
    // Notification Exchange and Queues
    public static final String NOTIFICATION_EXCHANGE = "notification.exchange";
    public static final String NOTIFICATION_QUEUE = "notification.queue";
    public static final String NOTIFICATION_ROUTING_KEY = "notification.routingkey";
    public static final String TABLE_RESERV_ROUTING_KEY = "table.reservation.*";

    @Bean
    public TopicExchange reservationExchange() {
        return new TopicExchange(RESERVATION_EXCHANGE);
    }

    @Bean
    public Queue reservationQueue() {
        return new Queue(RESERVATION_QUEUE, true);
    }

    @Bean
    public Queue tableReservQueue() {
        return new Queue(TABLE_RESERV_QUEUE, true);
    }

    @Bean
    public Binding reservationBinding(Queue reservationQueue, TopicExchange reservationExchange) {
        return BindingBuilder.bind(reservationQueue).to(reservationExchange).with(RESERVATION_ROUTING_KEY);
    }

    @Bean
    public Binding tableReservBinding(Queue tableReservQueue, TopicExchange reservationExchange) {
        return BindingBuilder.bind(tableReservQueue)
                .to(reservationExchange)
                .with(TABLE_RESERV_ROUTING_KEY);
    }

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(notificationQueue).to(notificationExchange).with(NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
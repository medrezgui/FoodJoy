package tn.esprit.foodjoy.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Nom des queues SANS avoir besoin à les déclarer
    public static final String RESERVATION_EXCHANGE = "reservation.exchange";
    public static final String TABLE_RESERV_QUEUE = "reservationQueue";
    public static final String RESERVATION_ROUTING_KEY = "reservation.routingkey";



    @Bean
    public Queue tableReservQueue() {
        return new Queue(TABLE_RESERV_QUEUE, true);
    }


    @Bean
    public TopicExchange reservationExchange() {
        return new TopicExchange(RESERVATION_EXCHANGE);
    }


    @Bean
    public Binding reservationBinding(Queue tableReservQueue, TopicExchange reservationExchange) {
        return BindingBuilder.bind(tableReservQueue)
                .to(reservationExchange)
                .with(RESERVATION_ROUTING_KEY);
    }

    // sérialisation JSON <-> POJO automatique pour RabbitTemplate
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate configuré pour utiliser le converter
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    // Factory for @RabbitListener
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory cf, MessageConverter converter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cf);
        factory.setMessageConverter(converter);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(3);
        return factory;
    }
}



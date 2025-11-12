package tn.esprit.foodjoy.commande.gestioncommande.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchange et Queue pour recevoir les plats de Menu_PlatMS
    public static final String MENU_PLAT_EXCHANGE = "menuplat.exchange";
    public static final String MENU_PLAT_QUEUE = "menuplat.queue";
    public static final String MENU_PLAT_ROUTING_KEY = "menuplat.routingkey";

    @Bean
    public TopicExchange menuPlatExchange() {
        return new TopicExchange(MENU_PLAT_EXCHANGE);
    }

    @Bean
    public Queue menuPlatQueue() {
        return new Queue(MENU_PLAT_QUEUE, true);
    }

    @Bean
    public Binding menuPlatBinding(Queue menuPlatQueue, TopicExchange menuPlatExchange) {
        return BindingBuilder.bind(menuPlatQueue).to(menuPlatExchange).with(MENU_PLAT_ROUTING_KEY);
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


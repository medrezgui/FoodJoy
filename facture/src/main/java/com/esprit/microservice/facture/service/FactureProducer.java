package com.esprit.microservice.facture.service;

import com.esprit.microservice.facture.config.RabbitMQConfig;
import com.esprit.microservice.facture.dto.FactureDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class FactureProducer {
    private final RabbitTemplate rabbitTemplate;
    private static final Logger log = LoggerFactory.getLogger(FactureProducer.class);

    // Inject RabbitTemplate (configuré avec Jackson converter)
    public FactureProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendFacture(FactureDTO facture) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.STOCK_FACTURE_QUEUE, facture);
            log.info("Facture envoyé à la queue : ", RabbitMQConfig.STOCK_FACTURE_QUEUE, facture);
        } catch (AmqpException e) {
            log.error("Erreur lors de l'envoi du job à RabbitMQ", e);
            // Remonter l'exception pour que l'appelant sache que l'envoi a échoué
            throw e;
        }
    }

}

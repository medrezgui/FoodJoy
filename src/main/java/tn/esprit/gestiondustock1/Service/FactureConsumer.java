package tn.esprit.gestiondustock1.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondustock1.DTO.FactureDTO;

import java.util.ArrayList;
import java.util.List;

import static tn.esprit.gestiondustock1.config.RabbitMQConfig.Stock_facture_QUEUE;

@Service
public class FactureConsumer {

    private static final Logger log = LoggerFactory.getLogger(FactureConsumer.class);

    // Liste pour stocker les factures reçues
    private final List<FactureDTO> receivedFactures = new ArrayList<>();

    // Méthode appelée automatiquement quand un message arrive dans la queue
    @RabbitListener(queues = Stock_facture_QUEUE)
    public void consumeFacture(FactureDTO factureDTO) {
        log.info("✅ Facture reçue depuis RabbitMQ : {}", factureDTO.getNumeroFacture());
        receivedFactures.add(factureDTO);
    }

    // Méthode pour retourner la liste des factures reçues
    public List<FactureDTO> getReceivedFactures() {
        return receivedFactures;
    }
}

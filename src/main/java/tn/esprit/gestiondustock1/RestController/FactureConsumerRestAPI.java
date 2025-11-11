package tn.esprit.gestiondustock1.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestiondustock1.DTO.FactureDTO;
import tn.esprit.gestiondustock1.Service.FactureConsumer;


import java.util.List;

@RestController
@RequestMapping("/factures")
public class FactureConsumerRestAPI {

    private final FactureConsumer factureConsumerService;

    public FactureConsumerRestAPI(FactureConsumer factureConsumerService) {
        this.factureConsumerService = factureConsumerService;
    }

    // Endpoint de test pour vérifier que le microservice tourne
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, je suis le microservice Facturation (Consumer RabbitMQ)";
    }

    // Endpoint pour voir les factures consommées depuis RabbitMQ
    @GetMapping("/received")
    public List<FactureDTO> getReceivedFactures() {
        return factureConsumerService.getReceivedFactures();
    }
}


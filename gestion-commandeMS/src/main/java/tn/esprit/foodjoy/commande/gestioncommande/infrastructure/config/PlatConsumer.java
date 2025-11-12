package tn.esprit.foodjoy.commande.gestioncommande.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tn.esprit.foodjoy.commande.gestioncommande.application.dto.PlatDTO;
import tn.esprit.foodjoy.commande.gestioncommande.application.service.PlatService;

/**
 * Consumer RabbitMQ pour recevoir les plats depuis Menu_PlatMS
 */
@Component
public class PlatConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PlatConsumer.class);
    
    private final PlatService platService;

    public PlatConsumer(PlatService platService) {
        this.platService = platService;
    }

    @RabbitListener(queues = RabbitMQConfig.MENU_PLAT_QUEUE)
    public void receivePlat(PlatDTO platDTO) {
        try {
            if (platDTO == null) {
                logger.error("❌ PlatDTO reçu est null!");
                return;
            }
            
            if (platDTO.getIdPlat() == null) {
                logger.error("❌ Plat reçu sans ID: {}", platDTO.getNomPlat());
                return;
            }
            
            logger.info("📥 Plat reçu via RabbitMQ: {} (ID: {}, Prix: {})", 
                platDTO.getNomPlat(), platDTO.getIdPlat(), platDTO.getPrix());
            
            // Sauvegarder ou mettre à jour le plat dans le cache local
            tn.esprit.foodjoy.commande.gestioncommande.domain.model.Plat savedPlat = platService.saveOrUpdatePlat(platDTO);
            
            logger.info("✅ Plat synchronisé avec succès: {} (ID: {})", savedPlat.getNomPlat(), savedPlat.getIdPlat());
        } catch (Exception e) {
            logger.error("❌ Erreur lors de la réception du plat (ID: {}, Nom: {}): {}", 
                platDTO != null ? platDTO.getIdPlat() : "null",
                platDTO != null ? platDTO.getNomPlat() : "null",
                e.getMessage(), e);
        }
    }
}


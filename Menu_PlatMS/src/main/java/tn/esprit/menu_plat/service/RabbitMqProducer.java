package tn.esprit.menu_plat.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tn.esprit.menu_plat.config.RabbitMQConfig;
import tn.esprit.menu_plat.dto.PlatDTO;
import tn.esprit.menu_plat.modal.Plat;

@Service
public class RabbitMqProducer {
    
    private final RabbitTemplate rabbitTemplate;

    public RabbitMqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Envoie un plat via RabbitMQ
     */
    public void sendPlat(Plat plat) {
        try {
            // Vérifier que le plat a un ID
            if (plat.getIdPlat() == null) {
                System.err.println("❌ ERREUR: Impossible d'envoyer le plat '" + plat.getNomPlat() + "' car il n'a pas d'ID!");
                return;
            }
            
            PlatDTO platDTO = convertToDTO(plat);
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.MENU_PLAT_EXCHANGE,
                RabbitMQConfig.MENU_PLAT_ROUTING_KEY,
                platDTO
            );
            System.out.println("✅ Plat envoyé via RabbitMQ: " + platDTO.getNomPlat() + " (ID: " + platDTO.getIdPlat() + ")");
        } catch (Exception e) {
            System.err.println("❌ ERREUR lors de l'envoi du plat '" + plat.getNomPlat() + "': " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-lancer l'exception pour que l'appelant soit informé
        }
    }

    /**
     * Envoie tous les plats disponibles
     */
    public void sendAllPlats(java.util.List<Plat> plats) {
        if (plats == null || plats.isEmpty()) {
            System.out.println("⚠️ Aucun plat à envoyer");
            return;
        }
        
        int successCount = 0;
        int errorCount = 0;
        
        for (Plat plat : plats) {
            try {
                sendPlat(plat);
                successCount++;
            } catch (Exception e) {
                errorCount++;
                System.err.println("❌ Échec d'envoi pour le plat: " + plat.getNomPlat());
            }
        }
        
        System.out.println("📊 Résumé d'envoi: " + successCount + " réussis, " + errorCount + " échecs sur " + plats.size() + " plats");
    }

    /**
     * Convertit un Plat en PlatDTO
     */
    private PlatDTO convertToDTO(Plat plat) {
        if (plat == null) {
            throw new IllegalArgumentException("Le plat ne peut pas être null");
        }
        
        PlatDTO dto = new PlatDTO();
        dto.setIdPlat(plat.getIdPlat());
        dto.setNomPlat(plat.getNomPlat());
        dto.setDescription(plat.getDescription());
        dto.setPrix(plat.getPrix());
        dto.setCategorie(plat.getCategorie());
        dto.setImageUrl(plat.getImageUrl());
        dto.setEstDisponible(plat.getEstDisponible());
        dto.setMenuId(plat.getMenu() != null ? plat.getMenu().getIdMenu() : null);
        
        // Vérification de l'intégrité des données
        if (dto.getIdPlat() == null) {
            throw new IllegalStateException("Le plat '" + plat.getNomPlat() + "' n'a pas d'ID après conversion!");
        }
        
        return dto;
    }
}

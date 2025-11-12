package tn.esprit.foodjoy.commande.gestioncommande.infrastructure.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.foodjoy.commande.gestioncommande.application.service.PlatService;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.Commande;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.LigneCommande;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.Plat;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.StatutCommande;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.TypeCommande;
import tn.esprit.foodjoy.commande.gestioncommande.infrastructure.repository.CommandeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CommandeRepository commandeRepository;
    private final PlatService platService;

    public DataInitializer(CommandeRepository commandeRepository, PlatService platService) {
        this.commandeRepository = commandeRepository;
        this.platService = platService;
    }

    @Override
    public void run(String... args) {
        // Attendre un peu pour que les plats soient reçus via RabbitMQ
        try {
            Thread.sleep(3000); // Attendre 3 secondes pour que RabbitMQ synchronise les plats
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Initialiser les données d'exemple uniquement si la base est vide
        if (commandeRepository.count() == 0) {
            initialiserDonneesExemple();
        }
    }

    private void initialiserDonneesExemple() {
        // Récupérer les plats disponibles depuis le cache
        List<Plat> platsDisponibles = platService.getAllPlatsDisponibles();
        
        if (platsDisponibles.isEmpty()) {
            System.out.println("⚠️ Aucun plat disponible. Les plats seront initialisés après réception via RabbitMQ.");
            return;
        }

        System.out.println("📦 " + platsDisponibles.size() + " plats disponibles pour initialiser les commandes d'exemple");

        // Commande 1: Sur place - EN_ATTENTE
        if (platsDisponibles.size() >= 2) {
            Commande commande1 = new Commande();
            commande1.setNumeroCommande("CMD-001-20241201");
            commande1.setStatut(StatutCommande.EN_ATTENTE);
            commande1.setTypeCommande(TypeCommande.SUR_PLACE);
            commande1.setDateCreation(LocalDateTime.now().minusHours(1));
            commande1.setEmployeId(1L);
            commande1.setTableId(5L);
            
            Plat plat1 = platsDisponibles.get(0);
            Plat plat2 = platsDisponibles.get(1);
            LigneCommande ligne1_1 = new LigneCommande(plat1.getIdPlat(), 2, plat1.getPrix(), "Bien cuit");
            LigneCommande ligne1_2 = new LigneCommande(plat2.getIdPlat(), 1, plat2.getPrix(), null);
            commande1.ajouterLigneCommande(ligne1_1);
            commande1.ajouterLigneCommande(ligne1_2);
            commandeRepository.save(commande1);
        }

        // Commande 2: À emporter - PREPARATION
        if (platsDisponibles.size() >= 3) {
            Commande commande2 = new Commande();
            commande2.setNumeroCommande("CMD-002-20241201");
            commande2.setStatut(StatutCommande.PREPARATION);
            commande2.setTypeCommande(TypeCommande.À_EMPORTER);
            commande2.setDateCreation(LocalDateTime.now().minusMinutes(30));
            commande2.setEmployeId(2L);
            commande2.setTableId(null);
            
            Plat plat3 = platsDisponibles.get(2);
            LigneCommande ligne2_1 = new LigneCommande(plat3.getIdPlat(), 3, plat3.getPrix(), "Sans oignons");
            commande2.ajouterLigneCommande(ligne2_1);
            commandeRepository.save(commande2);
        }

        // Commande 3: Livraison - PRETE
        if (platsDisponibles.size() >= 6) {
            Commande commande3 = new Commande();
            commande3.setNumeroCommande("CMD-003-20241201");
            commande3.setStatut(StatutCommande.PRETE);
            commande3.setTypeCommande(TypeCommande.LIVRAISON);
            commande3.setDateCreation(LocalDateTime.now().minusMinutes(15));
            commande3.setEmployeId(1L);
            commande3.setTableId(null);
            
            Plat plat4 = platsDisponibles.get(3);
            Plat plat5 = platsDisponibles.get(4);
            Plat plat6 = platsDisponibles.get(5);
            LigneCommande ligne3_1 = new LigneCommande(plat4.getIdPlat(), 1, plat4.getPrix(), "Sans gluten");
            LigneCommande ligne3_2 = new LigneCommande(plat5.getIdPlat(), 2, plat5.getPrix(), null);
            LigneCommande ligne3_3 = new LigneCommande(plat6.getIdPlat(), 1, plat6.getPrix(), null);
            commande3.ajouterLigneCommande(ligne3_1);
            commande3.ajouterLigneCommande(ligne3_2);
            commande3.ajouterLigneCommande(ligne3_3);
            commandeRepository.save(commande3);
        }

        // Commande 4: Sur place - SERVIE
        if (platsDisponibles.size() >= 7) {
            Commande commande4 = new Commande();
            commande4.setNumeroCommande("CMD-004-20241201");
            commande4.setStatut(StatutCommande.SERVIE);
            commande4.setTypeCommande(TypeCommande.SUR_PLACE);
            commande4.setDateCreation(LocalDateTime.now().minusHours(2));
            commande4.setEmployeId(3L);
            commande4.setTableId(10L);
            
            Plat plat7 = platsDisponibles.get(6);
            LigneCommande ligne4_1 = new LigneCommande(plat7.getIdPlat(), 2, plat7.getPrix(), "Sauce à part");
            commande4.ajouterLigneCommande(ligne4_1);
            commandeRepository.save(commande4);
        }

        // Commande 5: À emporter - EN_ATTENTE
        if (platsDisponibles.size() >= 9) {
            Commande commande5 = new Commande();
            commande5.setNumeroCommande("CMD-005-20241201");
            commande5.setStatut(StatutCommande.EN_ATTENTE);
            commande5.setTypeCommande(TypeCommande.À_EMPORTER);
            commande5.setDateCreation(LocalDateTime.now().minusMinutes(5));
            commande5.setEmployeId(2L);
            commande5.setTableId(null);
            
            Plat plat8 = platsDisponibles.get(7);
            Plat plat9 = platsDisponibles.get(8);
            LigneCommande ligne5_1 = new LigneCommande(plat8.getIdPlat(), 1, plat8.getPrix(), "Extra épicé");
            LigneCommande ligne5_2 = new LigneCommande(plat9.getIdPlat(), 1, plat9.getPrix(), null);
            commande5.ajouterLigneCommande(ligne5_1);
            commande5.ajouterLigneCommande(ligne5_2);
            commandeRepository.save(commande5);
        }

        System.out.println("✅ Données d'exemple initialisées avec succès!");
        System.out.println("   - " + commandeRepository.count() + " commandes créées");
        System.out.println("   - Lignes de commande associées avec plats dynamiques");
    }
}



package tn.esprit.menu_plat.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.menu_plat.modal.Menu;
import tn.esprit.menu_plat.modal.Plat;
import tn.esprit.menu_plat.repository.MenuRepository;
import tn.esprit.menu_plat.repository.PlatRepository;
import tn.esprit.menu_plat.service.RabbitMqProducer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MenuRepository menuRepository;
    private final PlatRepository platRepository;
    private final RabbitMqProducer rabbitMqProducer;

    public DataInitializer(MenuRepository menuRepository, PlatRepository platRepository, RabbitMqProducer rabbitMqProducer) {
        this.menuRepository = menuRepository;
        this.platRepository = platRepository;
        this.rabbitMqProducer = rabbitMqProducer;
    }

    @Override
    public void run(String... args) {
        // Attendre un peu pour que RabbitMQ soit prêt
        try {
            System.out.println("⏳ Attente de 2 secondes pour que RabbitMQ soit prêt...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("⚠️ Interruption pendant l'attente");
        }
        
        System.out.println("ℹ️ Vérification de la présence des données de référence (menus & plats) ...");
        boolean nouvellesDonneesCreees = assurerDonneesDeReference();

        if (nouvellesDonneesCreees) {
            System.out.println("✅ Données de référence mises à jour avec succès.");
        } else {
            System.out.println("ℹ️ Aucune nouvelle donnée à créer. Menu: " + menuRepository.count() + ", Plats: " + platRepository.count());
        }

        republierTousLesPlatsPourSynchronisation();
    }

    private boolean assurerDonneesDeReference() {
        // Assurer la présence des menus de base
        Menu menuPrincipal = obtenirOuCreerMenu(
                "Menu Principal",
                "Notre menu principal avec une sélection variée de plats",
                "Principal"
        );

        Menu menuDesserts = obtenirOuCreerMenu(
                "Menu Desserts",
                "Une sélection de desserts délicieux",
                "Desserts"
        );

        Menu menuEntrees = obtenirOuCreerMenu(
                "Menu Entrées",
                "Nos meilleures entrées",
                "Entrées"
        );

        // Préparer la liste des plats à vérifier
        List<PlatDefinition> definitions = Arrays.asList(
                // Plats principaux
                new PlatDefinition("Couscous Royal",
                        "Couscous traditionnel avec agneau, poulet et merguez, accompagné de légumes et de semoule",
                        25.00, "Plat Principal", "https://example.com/images/couscous.jpg", true, menuPrincipal),

                new PlatDefinition("Tajine d'Agneau aux Pruneaux",
                        "Tajine d'agneau mijoté avec des pruneaux, amandes et épices marocaines",
                        22.50, "Plat Principal", "https://example.com/images/tajine.jpg", true, menuPrincipal),

                new PlatDefinition("Pizza Margherita",
                        "Pizza classique avec tomate, mozzarella et basilic frais",
                        12.00, "Plat Principal", "https://example.com/images/pizza-margherita.jpg", true, menuPrincipal),

                new PlatDefinition("Burger Gourmet",
                        "Burger avec steak haché, fromage, salade, tomate et sauce spéciale",
                        15.50, "Plat Principal", "https://example.com/images/burger.jpg", true, menuPrincipal),

                new PlatDefinition("Pâtes Carbonara",
                        "Pâtes fraîches avec lardons, crème, parmesan et œuf",
                        14.00, "Plat Principal", "https://example.com/images/carbonara.jpg", true, menuPrincipal),

                new PlatDefinition("Salade César",
                        "Salade romaine avec poulet grillé, croûtons, parmesan et sauce césar",
                        11.00, "Plat Principal", "https://example.com/images/salade-cesar.jpg", true, menuPrincipal),

                new PlatDefinition("Poisson Grillé",
                        "Poisson frais grillé avec légumes de saison et riz pilaf",
                        18.00, "Plat Principal", "https://example.com/images/poisson-grille.jpg", true, menuPrincipal),

                // Entrées
                new PlatDefinition("Salade de Chèvre Chaud",
                        "Salade verte avec chèvre chaud sur toast et noix",
                        8.50, "Entrée", "https://example.com/images/chevre-chaud.jpg", true, menuEntrees),

                new PlatDefinition("Soupe à l'Oignon",
                        "Soupe traditionnelle française avec fromage gratiné",
                        7.00, "Entrée", "https://example.com/images/soupe-oignon.jpg", true, menuEntrees),

                // Desserts
                new PlatDefinition("Tiramisu",
                        "Dessert italien au café et mascarpone",
                        6.50, "Dessert", "https://example.com/images/tiramisu.jpg", true, menuDesserts),

                new PlatDefinition("Fondant au Chocolat",
                        "Gâteau au chocolat fondant avec cœur coulant",
                        7.00, "Dessert", "https://example.com/images/fondant-chocolat.jpg", true, menuDesserts),

                new PlatDefinition("Tarte aux Pommes",
                        "Tarte aux pommes maison avec cannelle",
                        5.50, "Dessert", "https://example.com/images/tarte-pommes.jpg", true, menuDesserts)
        );

        List<Plat> platsAAjouter = new java.util.ArrayList<>();

        for (PlatDefinition definition : definitions) {
            if (platRepository.existsByNomPlat(definition.nom())) {
                System.out.println("ℹ️ Plat déjà présent: " + definition.nom());
            } else {
                platsAAjouter.add(definition.toPlat());
            }
        }

        if (platsAAjouter.isEmpty()) {
            System.out.println("ℹ️ Aucun nouveau plat à ajouter. La base contient déjà les plats de référence.");
            return false;
        }

        System.out.println("💾 Sauvegarde de " + platsAAjouter.size() + " nouveaux plats dans la base de données...");
        List<Plat> savedPlats = platRepository.saveAll(platsAAjouter);

        // Vérifier que les plats ont bien des IDs
        System.out.println("🔍 Vérification des IDs des plats sauvegardés...");
        for (Plat plat : savedPlats) {
            if (plat.getIdPlat() == null) {
                System.err.println("❌ ERREUR: Le plat '" + plat.getNomPlat() + "' n'a pas d'ID!");
            } else {
                System.out.println("   ✓ Plat sauvegardé: " + plat.getNomPlat() + " (ID: " + plat.getIdPlat() + ")");
            }
        }

        return true;
    }

    private void republierTousLesPlatsPourSynchronisation() {
        List<Plat> tousLesPlats = platRepository.findAll();
        if (tousLesPlats.isEmpty()) {
            System.out.println("⚠️ Aucun plat disponible pour synchronisation RabbitMQ.");
            return;
        }

        System.out.println("📤 Publication de " + tousLesPlats.size() + " plat(s) via RabbitMQ pour synchronisation...");
        try {
            rabbitMqProducer.sendAllPlats(tousLesPlats);
            System.out.println("✅ Publication RabbitMQ terminée.");
        } catch (Exception e) {
            System.err.println("❌ ERREUR lors de la publication RabbitMQ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Menu obtenirOuCreerMenu(String nom, String description, String categorie) {
        return menuRepository.findByNomMenu(nom)
                .map(menu -> {
                    System.out.println("ℹ️ Menu déjà présent: " + nom);
                    return menu;
                })
                .orElseGet(() -> {
                    System.out.println("➕ Création du menu: " + nom);
                    Menu menu = new Menu();
                    menu.setNomMenu(nom);
                    menu.setDescription(description);
                    menu.setDateCreation(LocalDate.now());
                    menu.setEstActif(true);
                    menu.setCategorie(categorie);
                    return menuRepository.save(menu);
                });
    }

    private record PlatDefinition(String nom, String description, Double prix,
                                  String categorie, String imageUrl, Boolean estDisponible, Menu menu) {
        public Plat toPlat() {
            Plat plat = new Plat();
            plat.setNomPlat(nom);
            plat.setDescription(description);
            plat.setPrix(prix);
            plat.setCategorie(categorie);
            plat.setImageUrl(imageUrl);
            plat.setEstDisponible(estDisponible);
            plat.setMenu(menu);
            return plat;
        }
    }

}


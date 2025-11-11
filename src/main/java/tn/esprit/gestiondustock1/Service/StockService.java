package tn.esprit.gestiondustock1.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondustock1.Entity.Ingredient;
import tn.esprit.gestiondustock1.Entity.Stock;
import tn.esprit.gestiondustock1.Repository.IngredientRepository;
import tn.esprit.gestiondustock1.Repository.StockRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService implements IServiceStock {

    private final StockRepository stockRepository;
    private final EmailService emailService;

    @Autowired
    private IngredientRepository ingredientRepository;// ✅ ajout de l’EmailService


    /**
     * Add a new stock or update existing stock for an ingredient.
     * Input: Stock object (ingredient, quantity, expiry date)
     * Output: Saved Stock object
     */
    @Override
    public Stock addOrUpdateStock(Stock newStock) {
        if (newStock.getIngredient() == null || newStock.getIngredient().getId() == null) {
            throw new IllegalArgumentException("L'ingrédient est obligatoire !");
        }

        // Vérifie si un stock existe déjà pour cet ingrédient
        Optional<Stock> existingStockOpt = stockRepository.findByIngredientId(newStock.getIngredient().getId());

        Stock stockToSave;

        if (existingStockOpt.isPresent()) {
            // ✅ L'ingrédient existe déjà : on additionne les quantités
            Stock existingStock = existingStockOpt.get();
            existingStock.setQuantite(existingStock.getQuantite() + newStock.getQuantite());
            existingStock.setDateModification(LocalDateTime.now());
            existingStock.setDatePeremption(newStock.getDatePeremption());
            stockToSave = existingStock;
        } else {
            // 🆕 L'ingrédient n'existe pas encore : on crée un nouveau stock
            Ingredient ingredient = ingredientRepository.findById(newStock.getIngredient().getId())
                    .orElseThrow(() -> new RuntimeException("Ingrédient non trouvé avec l'ID : " + newStock.getIngredient().getId()));

            newStock.setIngredient(ingredient);
            newStock.setDateModification(LocalDateTime.now());
            stockToSave = newStock;
        }

        return stockRepository.save(stockToSave);
    }


    /**
     * Update an existing stock by ID.
     * Input: stock ID, Stock object (new values)
     * Output: Updated Stock object
     */
    @Override
    public Stock updateStock(Long id, Stock stock) {
        Stock existing = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock non trouvé"));
        existing.setQuantite(stock.getQuantite());
        existing.setIngredient(stock.getIngredient());
        existing.setDateModification(LocalDateTime.now());
        existing.setDatePeremption(stock.getDatePeremption());

        return stockRepository.save(existing);
    }

    /**
     * Delete stock by ID.
     * Input: stock ID
     * Output: void
     */
    @Override
    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    /**
     * Retrieve all stocks.
     * Input: none
     * Output: List of Stock objects
     */
    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    /**
     * Retrieve a stock by ID.
     * Input: stock ID
     * Output: Stock object
     */
    @Override
    public Stock getStockById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock non trouvé"));
    }
    /**
     * Verify stock alerts for low quantity or near expiration.
     * Input: none
     * Output: void
     * Sends email alerts via SendGrid if conditions met.
     */


        // Méthode pour vérifier les alertes
        public void verifierAlertes() {
            LocalDateTime dateLimite = LocalDateTime.now().plusDays(7);

            // Récupération des stocks faibles
            List<Stock> faibles = stockRepository.findStocksFaibles();

            // Récupération des stocks presque périmés
            List<Stock> perimes = stockRepository.findStocksPresquePerimes(dateLimite);

            // Envoi des emails pour stocks faibles
            for (Stock s : faibles) {
                envoyerEmailAlerte(s, "Le stock est faible !");
            }

            // Envoi des emails pour stocks presque périmés
            for (Stock s : perimes) {
                envoyerEmailAlerte(s, "Le stock est presque périmé !");
            }
        }

    // Méthode helper pour envoyer l'email
    private void envoyerEmailAlerte(Stock s, String message) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("ingredient", s.getIngredient().getNom());
        vars.put("quantite", s.getQuantite());
        vars.put("datePeremption", s.getDatePeremption().toString());
        vars.put("message", message);

        try {
            emailService.sendTemplateEmail(
                    "aflisarra19@gmail.com",
                    "d-cc39b7ae9bf14245815d712fda163291", // ID du template SendGrid
                    vars
            );
            System.out.println("✅ Email envoyé pour l'ingrédient : " + s.getIngredient().getNom());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        /**
         * Scheduled daily check for stock alerts.
         * Runs every day at 9 AM.
         * Input: none
         * Output: void
         */
        // Vérification automatique quotidienne à 9h
        @Scheduled(cron = "0 0 9 * * ?")
        public void verifierAlertesQuotidiennes() {
            System.out.println("📅 Vérification quotidienne des alertes de stock...");
            verifierAlertes();
        }
    }





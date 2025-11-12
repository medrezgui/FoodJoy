package tn.esprit.gestiondustock1.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondustock1.Entity.Ingredient;
import tn.esprit.gestiondustock1.Entity.Stock;
import tn.esprit.gestiondustock1.Repository.IngredientRepository;
import tn.esprit.gestiondustock1.Repository.StockRepository;


import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class IngredientService implements IServiceIngredient{
    private final IngredientRepository ingredientRepository;
    private final StockRepository stockRepository;


    /*@Override
    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }*/

    /**
     * Automatically add a new ingredient and create its stock.
     * Input: Ingredient object (name, unit, category, alert threshold)
     * Output: Saved Ingredient object
     */
    public Ingredient addIngredientAuto(Ingredient ingredient) {
        // 1️⃣ Sauvegarder l'ingrédient
        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        // 2️⃣ Créer automatiquement un stock associé
        Stock stock = new Stock();
        stock.setIngredient(savedIngredient);
        stock.setQuantite(0.0); // Quantité initiale = 0
        stock.setDateModification(LocalDateTime.now());
        stock.setDatePeremption(LocalDateTime.now().plusMonths(6)); // exemple : 6 mois plus tard

        // 3️⃣ Sauvegarder le stock
        stockRepository.save(stock);

        return savedIngredient;
    }

    /**
     * Update an existing ingredient.
     * Input: ingredient ID, Ingredient object (new values)
     * Output: Updated Ingredient object
     */
    @Override
    public Ingredient updateIngredient(Long id, Ingredient ingredient) {
        Ingredient existing = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient non trouvé"));
        existing.setNom(ingredient.getNom());
        existing.setUniteMesure(ingredient.getUniteMesure());
        existing.setSeuilAlerte(ingredient.getSeuilAlerte());
        existing.setCategorie(ingredient.getCategorie());
        return ingredientRepository.save(existing);
    }
    /**
     * Delete an ingredient by ID.
     * Input: ingredient ID
     * Output: void
     * Sends Kafka message to track history.
     */
    @Override
    public void deleteIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient non trouvé"));
        ingredientRepository.deleteById(id);


    }
    /**
     * Retrieve all ingredients.
     * Input: none
     * Output: List of Ingredient objects
     */
    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    /**
     * Retrieve an ingredient by ID.
     * Input: ingredient ID
     * Output: Ingredient object
     */
    @Override
    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient non trouvé"));
    }

    public List<Ingredient> rechercherParNom(String debutNom) {
        return ingredientRepository.findByNomStartingWithIgnoreCase(debutNom);
    }
}

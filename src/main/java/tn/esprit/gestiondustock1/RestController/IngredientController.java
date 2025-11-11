package tn.esprit.gestiondustock1.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondustock1.Entity.Ingredient;
import tn.esprit.gestiondustock1.Service.IServiceIngredient;


import java.util.List;
@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IServiceIngredient ingredientService; // injection via interface

    @PostMapping("/add")
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.addIngredientAuto(ingredient);
    }

    @PutMapping("/{id}")
    public Ingredient updateIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        return ingredientService.updateIngredient(id, ingredient);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
    }

    @GetMapping("/all")
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{id}")
    public Ingredient getIngredientById(@PathVariable Long id) {
        return ingredientService.getIngredientById(id);
    }

    // GET /api/ingredients/search?nom=su
    @GetMapping("/search")
    public List<Ingredient> rechercher(@RequestParam String nom) {
        return ingredientService.rechercherParNom(nom);
    }
}


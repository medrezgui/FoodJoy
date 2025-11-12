package tn.esprit.gestiondustock1.Service;


import tn.esprit.gestiondustock1.Entity.Ingredient;

import java.util.List;

public interface IServiceIngredient {
    Ingredient addIngredientAuto(Ingredient ingredient);
    /*Ingredient addIngredient(Ingredient ingredient);*/
    Ingredient updateIngredient(Long id, Ingredient ingredient);
    void deleteIngredient(Long id);
    List<Ingredient> getAllIngredients();
    Ingredient getIngredientById(Long id);

    List<Ingredient> rechercherParNom(String nom);
}

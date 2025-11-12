package tn.esprit.gestiondustock1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.gestiondustock1.Entity.Ingredient;


import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    // Recherche par début de nom
    List<Ingredient> findByNomStartingWithIgnoreCase(String prefix);
}

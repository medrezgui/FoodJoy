package tn.esprit.gestiondustock1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.gestiondustock1.Entity.Stock;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    // Trouver les stocks dont la quantité est inférieure au seuil de l’ingrédient
    @Query("SELECT s FROM Stock s WHERE s.quantite < s.ingredient.seuilAlerte")
    List<Stock> findStocksFaibles();

    // Trouver les stocks qui vont périmer dans les 7 prochains jours
    @Query("SELECT s FROM Stock s WHERE s.datePeremption <= :dateLimite")
    List<Stock> findStocksPresquePerimes(LocalDateTime dateLimite);

    Optional<Stock> findByIngredientId(Long ingredientId);
}


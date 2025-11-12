package tn.esprit.foodjoy.commande.gestioncommande.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.Plat;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlatRepository extends JpaRepository<Plat, Long> {
    
    Optional<Plat> findByIdPlat(Long idPlat);
    
    List<Plat> findByEstDisponibleTrue();
    
    List<Plat> findByCategorie(String categorie);
}


package tn.esprit.foodjoy.commande.gestioncommande.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.Commande;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.LigneCommande;

import java.util.List;

@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {

    List<LigneCommande> findByCommande(Commande commande);

    List<LigneCommande> findByPlatId(Long platId);

    @Query("SELECT lc FROM LigneCommande lc WHERE lc.commande.id = :commandeId")
    List<LigneCommande> findAllByCommandeId(@Param("commandeId") Long commandeId);

    @Modifying
    @Transactional
    @Query("DELETE FROM LigneCommande lc WHERE lc.commande.id = :commandeId")
    void deleteByCommandeId(@Param("commandeId") Long commandeId);
}



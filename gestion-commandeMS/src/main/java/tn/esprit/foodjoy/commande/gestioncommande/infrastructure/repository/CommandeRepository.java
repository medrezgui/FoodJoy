package tn.esprit.foodjoy.commande.gestioncommande.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.Commande;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.StatutCommande;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.TypeCommande;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    Optional<Commande> findByNumeroCommande(String numeroCommande);

    List<Commande> findByStatut(StatutCommande statut);

    List<Commande> findByTypeCommande(TypeCommande typeCommande);

    List<Commande> findByEmployeId(Long employeId);

    List<Commande> findByTableId(Long tableId);

    @Query("SELECT c FROM Commande c WHERE c.dateCreation BETWEEN :dateDebut AND :dateFin")
    List<Commande> findByDateCreationBetween(@Param("dateDebut") LocalDateTime dateDebut,
                                              @Param("dateFin") LocalDateTime dateFin);

    @Query("SELECT c FROM Commande c WHERE c.statut = :statut AND c.typeCommande = :typeCommande")
    List<Commande> findByStatutAndTypeCommande(@Param("statut") StatutCommande statut,
                                                 @Param("typeCommande") TypeCommande typeCommande);

    boolean existsByNumeroCommande(String numeroCommande);
}



// tn.esprit.foodjoy.repository.TableRepository
package tn.esprit.foodjoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tn.esprit.foodjoy.entity.TableResto;
import tn.esprit.foodjoy.entity.TableStatus;
import java.util.List;

@RepositoryRestResource
public interface TableRepository extends JpaRepository<TableResto, Long> {

    // Requêtes personnalisées (pour fonctions avancées)
    List<TableResto> findByStatus(TableStatus status);
    List<TableResto> findByAssignedServerId(Long serverId);
    List<TableResto> findByStatusAndCapacityGreaterThanEqual(TableStatus status, Integer minCapacity);
    List<TableResto> findByEspace_Id(Long espaceId);
    List<TableResto> findByEspace_Nom(String nom);
    List<TableResto> findByStatusAndEspace_Nom(TableStatus status, String nom);
}
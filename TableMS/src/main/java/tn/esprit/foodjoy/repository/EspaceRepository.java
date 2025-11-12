package tn.esprit.foodjoy.repository;

import tn.esprit.foodjoy.entity.Espace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface EspaceRepository extends JpaRepository<Espace, Long> {
    List<Espace> findByNomContainingIgnoreCase(String nom);
}

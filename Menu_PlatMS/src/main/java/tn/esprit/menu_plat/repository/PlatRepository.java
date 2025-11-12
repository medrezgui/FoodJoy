package tn.esprit.menu_plat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.menu_plat.modal.Plat;
@Repository
public interface PlatRepository extends JpaRepository<Plat, Long> {

    boolean existsByNomPlat(String nomPlat);
}

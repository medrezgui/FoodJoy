package tn.esprit.menu_plat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.menu_plat.modal.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    java.util.Optional<Menu> findByNomMenu(String nomMenu);
}

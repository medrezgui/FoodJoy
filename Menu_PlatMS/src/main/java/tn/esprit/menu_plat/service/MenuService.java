package tn.esprit.menu_plat.service;

import org.springframework.stereotype.Service;
import tn.esprit.menu_plat.modal.Menu;
import tn.esprit.menu_plat.modal.Plat;
import tn.esprit.menu_plat.repository.MenuRepository;

import java.util.List;

@Service
public class MenuService implements IMenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


@Override
    public Menu createMenu(Menu menu) {
        if (menu.getPlats() != null) {
            for (Plat plat : menu.getPlats()) {
                plat.setMenu(menu);
            }
        }
        return menuRepository.save(menu);
    }
    @Override

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }
    @Override

    public Menu getMenuById(Long idMenu) {
        return menuRepository.findById(idMenu).orElse(null);
    }
    @Override

    public void deleteMenu(Long idMenu) {
        menuRepository.deleteById(idMenu);
    }
    @Override
    public Menu updateMenu(Long idMenu, Menu updatedMenu) {
        Menu existingMenu = menuRepository.findById(idMenu).orElse(null);
        if (existingMenu == null) {
            return null;
        }

        existingMenu.setNomMenu(updatedMenu.getNomMenu());
        existingMenu.setDescription(updatedMenu.getDescription());
        existingMenu.setDateCreation(updatedMenu.getDateCreation());
        existingMenu.setEstActif(updatedMenu.getEstActif());
        existingMenu.setCategorie(updatedMenu.getCategorie());

        if (updatedMenu.getPlats() != null) {
            for (Plat plat : updatedMenu.getPlats()) {
                plat.setMenu(existingMenu);
            }
            existingMenu.setPlats(updatedMenu.getPlats());
        }

        return menuRepository.save(existingMenu);
    }

}

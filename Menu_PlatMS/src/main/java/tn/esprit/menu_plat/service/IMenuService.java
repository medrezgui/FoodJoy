package tn.esprit.menu_plat.service;

import tn.esprit.menu_plat.modal.Menu;

import java.util.List;

public interface IMenuService {

    List<Menu> getAllMenus();
    Menu getMenuById(Long id);
    Menu createMenu(Menu menu);
    Menu updateMenu(Long idMenu, Menu updatedMenu);
    void deleteMenu(Long id);
}

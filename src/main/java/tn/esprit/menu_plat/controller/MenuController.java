package tn.esprit.menu_plat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.menu_plat.modal.Menu;
import tn.esprit.menu_plat.modal.Plat;
import tn.esprit.menu_plat.service.IMenuService;

import java.util.List;

@RestController
@RequestMapping("/Menu")
@RequiredArgsConstructor
public class MenuController {

    private final IMenuService ims;

    @PostMapping("/createMenu")
    public Menu createMenu(@RequestBody Menu menu) {
        if (menu.getPlats() != null) {
            for (Plat plat : menu.getPlats()) {
                plat.setMenu(menu);
            }
        }

        return ims.createMenu(menu);
    }


    @GetMapping("/getAll")
    public List<Menu> getAllMenus() {
        return ims.getAllMenus();
    }

    @GetMapping("/{idMenu}")
    public Menu getMenuById(@PathVariable Long idMenu) {
        return ims.getMenuById(idMenu);
    }

    @DeleteMapping("/{idMenu}")
    public void deleteMenu(@PathVariable Long idMenu) {
        ims.deleteMenu(idMenu);
    }
}

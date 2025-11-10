package tn.esprit.menu_plat.controller;

import org.springframework.web.bind.annotation.*;
import tn.esprit.menu_plat.modal.Plat;
import tn.esprit.menu_plat.service.IPlatService;

import java.util.List;

@RestController
@RequestMapping("/Plat")
public class PlatController {

    private  final IPlatService ipl;

    public PlatController(IPlatService ipl) {
        this.ipl = ipl;
    }


    // ✅ Récupérer tous les plats
    @GetMapping("/all")
    public List<Plat> getAllPlats() {
        return ipl.getAllPlats();
    }

    // ✅ Récupérer un plat par ID
    @GetMapping("/{idPlat}")
    public Plat getPlatById(@PathVariable Long idPlat) {
        return ipl.getPlatById(idPlat);
    }

    // ✅ Créer un plat et l’associer à un menu
    @PostMapping("/create/{menuId}")
    public Plat createPlat(@RequestBody Plat plat, @PathVariable Long menuId) {
        return ipl.createPlat(plat, menuId);
    }

    // ✅ Modifier un plat
    @PutMapping("/update/{idPlat}")
    public Plat updatePlat(@PathVariable Long idPlat, @RequestBody Plat plat) {
        return ipl.updatePlat(idPlat, plat);
    }

    // ✅ Supprimer un plat
    @DeleteMapping("/delete/{idPlat}")
    public void deletePlat(@PathVariable Long idPlat) {
        ipl.deletePlat(idPlat);
    }
}

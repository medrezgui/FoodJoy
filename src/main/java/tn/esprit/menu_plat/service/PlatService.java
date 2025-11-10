package tn.esprit.menu_plat.service;

import org.springframework.stereotype.Service;
import tn.esprit.menu_plat.modal.Menu;
import tn.esprit.menu_plat.modal.Plat;
import tn.esprit.menu_plat.repository.MenuRepository;
import tn.esprit.menu_plat.repository.PlatRepository;

import java.util.List;

@Service
public class PlatService implements IPlatService {
    private final PlatRepository pl;
    private final MenuRepository mr;

    public PlatService(PlatRepository platRepository, MenuRepository menuRepository) {
        this.pl = platRepository;
        this.mr = menuRepository;
    }

    @Override
    public List<Plat> getAllPlats() {
        return pl.findAll();
    }
    @Override

    public Plat getPlatById(Long idPlat) {
        return pl.findById(idPlat).orElse(null);
    }
    @Override

    public Plat createPlat(Plat plat, Long menuId) {
        if (menuId != null) {
            Menu menu = mr.findById(menuId).orElse(null);
            if (menu != null) {
                plat.setMenu(menu);
            }
        }
        return pl.save(plat);
    }
    @Override

    public Plat updatePlat(Long idPlat, Plat updatedPlat) {
        Plat existingPlat = getPlatById(idPlat);
        if (existingPlat == null) return null;

        existingPlat.setNomPlat(updatedPlat.getNomPlat());
        existingPlat.setDescription(updatedPlat.getDescription());
        existingPlat.setPrix(updatedPlat.getPrix());
        existingPlat.setCategorie(updatedPlat.getCategorie());
        existingPlat.setImageUrl(updatedPlat.getImageUrl());
        existingPlat.setEstDisponible(updatedPlat.getEstDisponible());

        return pl.save(existingPlat);
    }
    @Override

    public void deletePlat(Long idPlat) {
        pl.deleteById(idPlat);
    }
}

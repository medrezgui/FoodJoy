package tn.esprit.menu_plat.service;

import tn.esprit.menu_plat.modal.Plat;

import java.util.List;

public interface IPlatService {

    List<Plat> getAllPlats();
    Plat getPlatById(Long idPlat);
    Plat createPlat(Plat plat, Long menuId);
    Plat updatePlat(Long idPlat, Plat updatedPlat);
    void deletePlat(Long idPlat);
}

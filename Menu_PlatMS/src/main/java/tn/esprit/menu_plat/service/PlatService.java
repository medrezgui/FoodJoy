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
    private final RabbitMqProducer rabbitMqProducer;

    public PlatService(PlatRepository platRepository, MenuRepository menuRepository, RabbitMqProducer rabbitMqProducer) {
        this.pl = platRepository;
        this.mr = menuRepository;
        this.rabbitMqProducer = rabbitMqProducer;
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
        Plat savedPlat = pl.save(plat);
        // Envoyer le plat via RabbitMQ
        rabbitMqProducer.sendPlat(savedPlat);
        return savedPlat;
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

        Plat savedPlat = pl.save(existingPlat);
        // Envoyer le plat mis à jour via RabbitMQ
        rabbitMqProducer.sendPlat(savedPlat);
        return savedPlat;
    }
    @Override

    public void deletePlat(Long idPlat) {
        pl.deleteById(idPlat);
    }
}

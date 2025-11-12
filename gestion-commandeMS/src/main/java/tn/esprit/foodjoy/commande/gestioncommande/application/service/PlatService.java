package tn.esprit.foodjoy.commande.gestioncommande.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tn.esprit.foodjoy.commande.gestioncommande.application.dto.PlatDTO;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.Plat;
import tn.esprit.foodjoy.commande.gestioncommande.infrastructure.repository.PlatRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlatService {

    private static final Logger logger = LoggerFactory.getLogger(PlatService.class);
    private final PlatRepository platRepository;

    public PlatService(PlatRepository platRepository) {
        this.platRepository = platRepository;
    }

    /**
     * Sauvegarde ou met à jour un plat dans le cache local
     */
    public Plat saveOrUpdatePlat(PlatDTO platDTO) {
        if (platDTO == null) {
            throw new IllegalArgumentException("PlatDTO ne peut pas être null");
        }
        
        if (platDTO.getIdPlat() == null) {
            throw new IllegalArgumentException("L'ID du plat ne peut pas être null");
        }
        
        // Vérifier si le plat existe déjà
        Optional<Plat> existingPlat = platRepository.findByIdPlat(platDTO.getIdPlat());
        
        Plat plat = convertDTOToEntity(platDTO);
        Plat savedPlat = platRepository.save(plat);
        
        if (existingPlat.isPresent()) {
            logger.info("🔄 Plat mis à jour dans le cache: {} (ID: {})", savedPlat.getNomPlat(), savedPlat.getIdPlat());
        } else {
            logger.info("➕ Nouveau plat ajouté au cache: {} (ID: {})", savedPlat.getNomPlat(), savedPlat.getIdPlat());
        }
        
        return savedPlat;
    }

    /**
     * Récupère un plat par son ID
     */
    public Optional<Plat> getPlatById(Long idPlat) {
        return platRepository.findByIdPlat(idPlat);
    }

    /**
     * Récupère tous les plats disponibles
     */
    public List<Plat> getAllPlatsDisponibles() {
        return platRepository.findByEstDisponibleTrue();
    }

    /**
     * Récupère tous les plats
     */
    public List<Plat> getAllPlats() {
        return platRepository.findAll();
    }

    /**
     * Récupère les plats par catégorie
     */
    public List<Plat> getPlatsByCategorie(String categorie) {
        return platRepository.findByCategorie(categorie);
    }

    /**
     * Vérifie si un plat existe et est disponible
     */
    public boolean isPlatDisponible(Long idPlat) {
        return platRepository.findByIdPlat(idPlat)
                .map(Plat::getEstDisponible)
                .orElse(false);
    }

    /**
     * Récupère le prix d'un plat
     */
    public Double getPrixPlat(Long idPlat) {
        return platRepository.findByIdPlat(idPlat)
                .map(Plat::getPrix)
                .orElse(null);
    }

    /**
     * Convertit un PlatDTO en entité Plat
     */
    private Plat convertDTOToEntity(PlatDTO dto) {
        Plat plat = new Plat();
        plat.setIdPlat(dto.getIdPlat());
        plat.setNomPlat(dto.getNomPlat());
        plat.setDescription(dto.getDescription());
        plat.setPrix(dto.getPrix());
        plat.setCategorie(dto.getCategorie());
        plat.setImageUrl(dto.getImageUrl());
        plat.setEstDisponible(dto.getEstDisponible());
        plat.setMenuId(dto.getMenuId());
        return plat;
    }
}


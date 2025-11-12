package tn.esprit.foodjoy.commande.gestioncommande.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.foodjoy.commande.gestioncommande.application.dto.LigneCommandeDTO;
import tn.esprit.foodjoy.commande.gestioncommande.application.mapper.CommandeMapper;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.Commande;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.LigneCommande;
import tn.esprit.foodjoy.commande.gestioncommande.infrastructure.repository.CommandeRepository;
import tn.esprit.foodjoy.commande.gestioncommande.infrastructure.repository.LigneCommandeRepository;

import java.util.List;

@Service
@Transactional
public class LigneCommandeService {

    private final LigneCommandeRepository ligneCommandeRepository;
    private final CommandeRepository commandeRepository;
    private final CommandeMapper commandeMapper;
    private final PlatService platService;

    public LigneCommandeService(LigneCommandeRepository ligneCommandeRepository,
                                CommandeRepository commandeRepository,
                                CommandeMapper commandeMapper,
                                PlatService platService) {
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.commandeRepository = commandeRepository;
        this.commandeMapper = commandeMapper;
        this.platService = platService;
    }

    // CRUD Operations

    public LigneCommandeDTO creerLigneCommande(Long commandeId, LigneCommandeDTO ligneCommandeDTO) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID: " + commandeId));

        // Valider que le plat existe et est disponible
        if (!platService.isPlatDisponible(ligneCommandeDTO.getPlatId())) {
            throw new RuntimeException("Le plat avec l'ID " + ligneCommandeDTO.getPlatId() + " n'existe pas ou n'est pas disponible");
        }
        
        // Récupérer le prix depuis le service si non fourni
        if (ligneCommandeDTO.getPrixUnitaire() == null) {
            Double prix = platService.getPrixPlat(ligneCommandeDTO.getPlatId());
            if (prix == null) {
                throw new RuntimeException("Impossible de récupérer le prix du plat avec l'ID " + ligneCommandeDTO.getPlatId());
            }
            ligneCommandeDTO.setPrixUnitaire(prix);
        }

        LigneCommande ligneCommande = commandeMapper.toLigneEntity(ligneCommandeDTO);
        ligneCommande.setCommande(commande);
        
        LigneCommande savedLigne = ligneCommandeRepository.save(ligneCommande);
        return commandeMapper.toLigneDTO(savedLigne);
    }

    @Transactional(readOnly = true)
    public LigneCommandeDTO trouverLigneCommandeParId(Long id) {
        LigneCommande ligneCommande = ligneCommandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ligne de commande non trouvée avec l'ID: " + id));
        return commandeMapper.toLigneDTO(ligneCommande);
    }

    @Transactional(readOnly = true)
    public List<LigneCommandeDTO> trouverToutesLesLignesCommande() {
        List<LigneCommande> lignes = ligneCommandeRepository.findAll();
        return lignes.stream()
                .map(commandeMapper::toLigneDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LigneCommandeDTO> trouverLignesCommandeParCommandeId(Long commandeId) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID: " + commandeId));
        
        List<LigneCommande> lignes = ligneCommandeRepository.findByCommande(commande);
        return lignes.stream()
                .map(commandeMapper::toLigneDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LigneCommandeDTO> trouverLignesCommandeParPlatId(Long platId) {
        List<LigneCommande> lignes = ligneCommandeRepository.findByPlatId(platId);
        return lignes.stream()
                .map(commandeMapper::toLigneDTO)
                .toList();
    }

    public LigneCommandeDTO mettreAJourLigneCommande(Long id, LigneCommandeDTO ligneCommandeDTO) {
        LigneCommande ligneCommande = ligneCommandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ligne de commande non trouvée avec l'ID: " + id));

        // Valider que le plat existe et est disponible si l'ID a changé
        if (!ligneCommande.getPlatId().equals(ligneCommandeDTO.getPlatId())) {
            if (!platService.isPlatDisponible(ligneCommandeDTO.getPlatId())) {
                throw new RuntimeException("Le plat avec l'ID " + ligneCommandeDTO.getPlatId() + " n'existe pas ou n'est pas disponible");
            }
        }
        
        // Récupérer le prix depuis le service si non fourni
        if (ligneCommandeDTO.getPrixUnitaire() == null) {
            Double prix = platService.getPrixPlat(ligneCommandeDTO.getPlatId());
            if (prix == null) {
                throw new RuntimeException("Impossible de récupérer le prix du plat avec l'ID " + ligneCommandeDTO.getPlatId());
            }
            ligneCommandeDTO.setPrixUnitaire(prix);
        }

        ligneCommande.setPlatId(ligneCommandeDTO.getPlatId());
        ligneCommande.setQuantite(ligneCommandeDTO.getQuantite());
        ligneCommande.setPrixUnitaire(ligneCommandeDTO.getPrixUnitaire());
        ligneCommande.setCommentaire(ligneCommandeDTO.getCommentaire());

        LigneCommande updatedLigne = ligneCommandeRepository.save(ligneCommande);
        return commandeMapper.toLigneDTO(updatedLigne);
    }

    public void supprimerLigneCommande(Long id) {
        if (!ligneCommandeRepository.existsById(id)) {
            throw new RuntimeException("Ligne de commande non trouvée avec l'ID: " + id);
        }
        ligneCommandeRepository.deleteById(id);
    }

    public void supprimerToutesLesLignesCommandeParCommandeId(Long commandeId) {
        if (!commandeRepository.existsById(commandeId)) {
            throw new RuntimeException("Commande non trouvée avec l'ID: " + commandeId);
        }
        ligneCommandeRepository.deleteByCommandeId(commandeId);
    }
}


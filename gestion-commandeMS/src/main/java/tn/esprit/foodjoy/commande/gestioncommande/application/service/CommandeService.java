package tn.esprit.foodjoy.commande.gestioncommande.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.foodjoy.commande.gestioncommande.application.dto.CommandeDTO;
import tn.esprit.foodjoy.commande.gestioncommande.application.dto.CommandeRequestDTO;
import tn.esprit.foodjoy.commande.gestioncommande.application.dto.StatutUpdateDTO;
import tn.esprit.foodjoy.commande.gestioncommande.application.mapper.CommandeMapper;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.Commande;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.LigneCommande;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.StatutCommande;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.TypeCommande;
import tn.esprit.foodjoy.commande.gestioncommande.infrastructure.repository.CommandeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final CommandeMapper commandeMapper;
    private final PlatService platService;

    public CommandeService(CommandeRepository commandeRepository,
                          CommandeMapper commandeMapper,
                          PlatService platService) {
        this.commandeRepository = commandeRepository;
        this.commandeMapper = commandeMapper;
        this.platService = platService;
    }

    // CRUD Operations

    public CommandeDTO creerCommande(CommandeRequestDTO requestDTO) {
        // Générer un numéro de commande unique
        String numeroCommande = genererNumeroCommande();

        // Vérifier l'unicité
        if (commandeRepository.existsByNumeroCommande(numeroCommande)) {
            numeroCommande = genererNumeroCommande();
        }

        // Créer la commande
        Commande commande = new Commande();
        commande.setNumeroCommande(numeroCommande);
        commande.setTypeCommande(TypeCommande.valueOf(requestDTO.getTypeCommande()));
        commande.setEmployeId(requestDTO.getEmployeId());
        commande.setTableId(requestDTO.getTableId());
        commande.setDateCreation(LocalDateTime.now());
        commande.setStatut(StatutCommande.EN_ATTENTE);

        // Ajouter les lignes de commande avec validation des plats
        if (requestDTO.getLignesCommande() != null) {
            for (var ligneDTO : requestDTO.getLignesCommande()) {
                // Valider que le plat existe et est disponible
                if (!platService.isPlatDisponible(ligneDTO.getPlatId())) {
                    throw new RuntimeException("Le plat avec l'ID " + ligneDTO.getPlatId() + " n'existe pas ou n'est pas disponible");
                }
                
                // Récupérer le prix depuis le service si non fourni
                if (ligneDTO.getPrixUnitaire() == null) {
                    Double prix = platService.getPrixPlat(ligneDTO.getPlatId());
                    if (prix == null) {
                        throw new RuntimeException("Impossible de récupérer le prix du plat avec l'ID " + ligneDTO.getPlatId());
                    }
                    ligneDTO.setPrixUnitaire(prix);
                }
                
                LigneCommande ligne = commandeMapper.toLigneEntity(ligneDTO);
                commande.ajouterLigneCommande(ligne);
            }
        }

        Commande savedCommande = commandeRepository.save(commande);
        return commandeMapper.toDTO(savedCommande);
    }

    @Transactional(readOnly = true)
    public CommandeDTO trouverCommandeParId(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID: " + id));
        return commandeMapper.toDTO(commande);
    }

    @Transactional(readOnly = true)
    public CommandeDTO trouverCommandeParNumero(String numeroCommande) {
        Commande commande = commandeRepository.findByNumeroCommande(numeroCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec le numéro: " + numeroCommande));
        return commandeMapper.toDTO(commande);
    }

    @Transactional(readOnly = true)
    public List<CommandeDTO> trouverToutesLesCommandes() {
        List<Commande> commandes = commandeRepository.findAll();
        return commandeMapper.toDTOList(commandes);
    }

    public CommandeDTO mettreAJourCommande(Long id, CommandeRequestDTO requestDTO) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID: " + id));

        // Mettre à jour les propriétés
        commande.setTypeCommande(TypeCommande.valueOf(requestDTO.getTypeCommande()));
        commande.setEmployeId(requestDTO.getEmployeId());
        commande.setTableId(requestDTO.getTableId());

        // Mettre à jour les lignes de commande avec validation des plats
        commande.getLignesCommande().clear();
        if (requestDTO.getLignesCommande() != null) {
            for (var ligneDTO : requestDTO.getLignesCommande()) {
                // Valider que le plat existe et est disponible
                if (!platService.isPlatDisponible(ligneDTO.getPlatId())) {
                    throw new RuntimeException("Le plat avec l'ID " + ligneDTO.getPlatId() + " n'existe pas ou n'est pas disponible");
                }
                
                // Récupérer le prix depuis le service si non fourni
                if (ligneDTO.getPrixUnitaire() == null) {
                    Double prix = platService.getPrixPlat(ligneDTO.getPlatId());
                    if (prix == null) {
                        throw new RuntimeException("Impossible de récupérer le prix du plat avec l'ID " + ligneDTO.getPlatId());
                    }
                    ligneDTO.setPrixUnitaire(prix);
                }
                
                LigneCommande ligne = commandeMapper.toLigneEntity(ligneDTO);
                commande.ajouterLigneCommande(ligne);
            }
        }

        Commande updatedCommande = commandeRepository.save(commande);
        return commandeMapper.toDTO(updatedCommande);
    }

    public void supprimerCommande(Long id) {
        if (!commandeRepository.existsById(id)) {
            throw new RuntimeException("Commande non trouvée avec l'ID: " + id);
        }
        commandeRepository.deleteById(id);
    }

    // Méthodes avancées

    /**
     * Méthode avancée 1: Changer le statut d'une commande
     */
    public CommandeDTO changerStatutCommande(Long id, StatutUpdateDTO statutUpdateDTO) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID: " + id));

        try {
            StatutCommande nouveauStatut = StatutCommande.valueOf(statutUpdateDTO.getNouveauStatut());
            commande.setStatut(nouveauStatut);
            Commande updatedCommande = commandeRepository.save(commande);
            return commandeMapper.toDTO(updatedCommande);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Statut invalide: " + statutUpdateDTO.getNouveauStatut());
        }
    }

    /**
     * Méthode avancée 2: Calculer le total d'une commande avec ses lignes
     */
    @Transactional(readOnly = true)
    public Double calculerTotalCommande(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID: " + id));

        return commande.calculerTotal();
    }

    // Méthodes de recherche

    @Transactional(readOnly = true)
    public List<CommandeDTO> trouverCommandesParStatut(String statut) {
        StatutCommande statutCommande = StatutCommande.valueOf(statut);
        List<Commande> commandes = commandeRepository.findByStatut(statutCommande);
        return commandeMapper.toDTOList(commandes);
    }

    @Transactional(readOnly = true)
    public List<CommandeDTO> trouverCommandesParType(String type) {
        TypeCommande typeCommande = TypeCommande.valueOf(type);
        List<Commande> commandes = commandeRepository.findByTypeCommande(typeCommande);
        return commandeMapper.toDTOList(commandes);
    }

    @Transactional(readOnly = true)
    public List<CommandeDTO> trouverCommandesParEmploye(Long employeId) {
        List<Commande> commandes = commandeRepository.findByEmployeId(employeId);
        return commandeMapper.toDTOList(commandes);
    }

    @Transactional(readOnly = true)
    public List<CommandeDTO> trouverCommandesParTable(Long tableId) {
        List<Commande> commandes = commandeRepository.findByTableId(tableId);
        return commandeMapper.toDTOList(commandes);
    }

    // Méthode utilitaire
    private String genererNumeroCommande() {
        return "CMD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase() + "-" + 
               LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}


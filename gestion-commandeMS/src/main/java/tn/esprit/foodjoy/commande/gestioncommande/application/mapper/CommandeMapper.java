package tn.esprit.foodjoy.commande.gestioncommande.application.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.foodjoy.commande.gestioncommande.application.dto.CommandeDTO;
import tn.esprit.foodjoy.commande.gestioncommande.application.dto.LigneCommandeDTO;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.Commande;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.LigneCommande;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.StatutCommande;
import tn.esprit.foodjoy.commande.gestioncommande.domain.model.TypeCommande;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommandeMapper {

    public CommandeDTO toDTO(Commande commande) {
        if (commande == null) {
            return null;
        }

        CommandeDTO dto = new CommandeDTO();
        dto.setId(commande.getId());
        dto.setNumeroCommande(commande.getNumeroCommande());
        dto.setStatut(commande.getStatut() != null ? commande.getStatut().name() : null);
        dto.setDateCreation(commande.getDateCreation());
        dto.setTypeCommande(commande.getTypeCommande() != null ? commande.getTypeCommande().name() : null);
        dto.setEmployeId(commande.getEmployeId());
        dto.setTableId(commande.getTableId());
        dto.setTotal(commande.calculerTotal());

        if (commande.getLignesCommande() != null) {
            dto.setLignesCommande(commande.getLignesCommande().stream()
                    .map(this::toLigneDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Commande toEntity(CommandeDTO dto) {
        if (dto == null) {
            return null;
        }

        Commande commande = new Commande();
        commande.setId(dto.getId());
        commande.setNumeroCommande(dto.getNumeroCommande());
        commande.setStatut(dto.getStatut() != null ? StatutCommande.valueOf(dto.getStatut()) : StatutCommande.EN_ATTENTE);
        commande.setDateCreation(dto.getDateCreation());
        commande.setTypeCommande(dto.getTypeCommande() != null ? TypeCommande.valueOf(dto.getTypeCommande()) : null);
        commande.setEmployeId(dto.getEmployeId());
        commande.setTableId(dto.getTableId());

        if (dto.getLignesCommande() != null) {
            List<LigneCommande> lignes = dto.getLignesCommande().stream()
                    .map(this::toLigneEntity)
                    .collect(Collectors.toList());
            commande.setLignesCommande(lignes);
            // La relation commande sera gérée automatiquement par JPA via cascade
            lignes.forEach(ligne -> ligne.setCommande(commande));
        }

        return commande;
    }

    public LigneCommandeDTO toLigneDTO(LigneCommande ligneCommande) {
        if (ligneCommande == null) {
            return null;
        }

        LigneCommandeDTO dto = new LigneCommandeDTO();
        dto.setId(ligneCommande.getId());
        dto.setPlatId(ligneCommande.getPlatId());
        dto.setQuantite(ligneCommande.getQuantite());
        dto.setPrixUnitaire(ligneCommande.getPrixUnitaire());
        dto.setCommentaire(ligneCommande.getCommentaire());
        dto.setSousTotal(ligneCommande.getSousTotal());

        return dto;
    }

    public LigneCommande toLigneEntity(LigneCommandeDTO dto) {
        if (dto == null) {
            return null;
        }

        LigneCommande ligneCommande = new LigneCommande();
        ligneCommande.setId(dto.getId());
        ligneCommande.setPlatId(dto.getPlatId());
        ligneCommande.setQuantite(dto.getQuantite());
        ligneCommande.setPrixUnitaire(dto.getPrixUnitaire());
        ligneCommande.setCommentaire(dto.getCommentaire());

        return ligneCommande;
    }

    public List<CommandeDTO> toDTOList(List<Commande> commandes) {
        return commandes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}



package tn.esprit.foodjoy.commande.gestioncommande.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "commande", uniqueConstraints = {
    @UniqueConstraint(columnNames = "numero_commande")
})
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_commande", nullable = false, unique = true, length = 50)
    private String numeroCommande;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutCommande statut;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_commande", nullable = false, length = 20)
    private TypeCommande typeCommande;

    @Column(name = "employe_id")
    private Long employeId;

    @Column(name = "table_id")
    private Long tableId;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LigneCommande> lignesCommande = new ArrayList<>();

    // Constructeurs
    public Commande() {
        this.dateCreation = LocalDateTime.now();
        this.statut = StatutCommande.EN_ATTENTE;
    }

    public Commande(String numeroCommande, TypeCommande typeCommande, Long employeId, Long tableId) {
        this();
        this.numeroCommande = numeroCommande;
        this.typeCommande = typeCommande;
        this.employeId = employeId;
        this.tableId = tableId;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public StatutCommande getStatut() {
        return statut;
    }

    public void setStatut(StatutCommande statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public TypeCommande getTypeCommande() {
        return typeCommande;
    }

    public void setTypeCommande(TypeCommande typeCommande) {
        this.typeCommande = typeCommande;
    }

    public Long getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public List<LigneCommande> getLignesCommande() {
        return lignesCommande;
    }

    public void setLignesCommande(List<LigneCommande> lignesCommande) {
        this.lignesCommande = lignesCommande;
    }

    // Méthodes utilitaires
    public void ajouterLigneCommande(LigneCommande ligneCommande) {
        this.lignesCommande.add(ligneCommande);
        ligneCommande.setCommande(this);
        // JPA gérera automatiquement commandeId lors de la sauvegarde via la relation
    }

    public void retirerLigneCommande(LigneCommande ligneCommande) {
        this.lignesCommande.remove(ligneCommande);
        ligneCommande.setCommande(null);
    }

    public Double calculerTotal() {
        return lignesCommande.stream()
                .mapToDouble(LigneCommande::getSousTotal)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commande commande = (Commande) o;
        return Objects.equals(id, commande.id) &&
               Objects.equals(numeroCommande, commande.numeroCommande);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroCommande);
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", numeroCommande='" + numeroCommande + '\'' +
                ", statut=" + statut +
                ", typeCommande=" + typeCommande +
                ", total=" + calculerTotal() +
                '}';
    }
}



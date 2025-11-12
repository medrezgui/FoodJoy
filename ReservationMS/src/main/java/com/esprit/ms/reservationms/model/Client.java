package com.esprit.ms.reservationms.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(unique = true, nullable = false)
    private String email;

    private String telephone;

    @Column(name = "date_inscription")
    private LocalDate dateInscription = LocalDate.now();

    @Column(name = "nombre_visites")
    private Integer nombreVisites = 0;

    // Constructeurs
    public Client() {}

    public Client(String nom, String email, String telephone) {
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
    }

    // Getters et Setters
    public Long getId() { return id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public LocalDate getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDate dateInscription) { this.dateInscription = dateInscription; }

    public Integer getNombreVisites() { return nombreVisites; }
    public void setNombreVisites(Integer nombreVisites) { this.nombreVisites = nombreVisites; }
}
package com.diallobank.model;

import jakarta.persistence.*;

@Entity
@Table(name = "compte")
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer montant;

    @Column(name = "nom_compte", length = 255)
    private String nomCompte;

    @Column(name = "num_card")
    private Long numCard;

    @Column(name = "type_compte", length = 255)
    private String typeCompte;

    private Double solde;

    @Column(name = "user_id")
    private String userId; // 🔹 Multi-tenant

    // ✅ Constructeur vide obligatoire pour JPA
    public Compte() {}

    // ✅ Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMontant() { return montant; }
    public void setMontant(Integer montant) { this.montant = montant; }

    public String getNomCompte() { return nomCompte; }
    public void setNomCompte(String nomCompte) { this.nomCompte = nomCompte; }

    public Long getNumCard() { return numCard; }
    public void setNumCard(Long numCard) { this.numCard = numCard; }

    public String getTypeCompte() { return typeCompte; }
    public void setTypeCompte(String typeCompte) { this.typeCompte = typeCompte; }

    public Double getSolde() { return solde; }
    public void setSolde(Double solde) { this.solde = solde; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}

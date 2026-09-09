package com.diallobank.model;

import jakarta.persistence.*;

@Entity
@Table(name = "prets")
public class Pret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String client;

    private Double montant;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @Column(name = "user_id")
    private String userId; // 🔹 Multi-tenant

    // ✅ Constructeur vide obligatoire pour JPA
    public Pret() {}

    // ✅ Enum pour statut
    public enum Statut {
        APPROUVE,
        EN_ATTENTE,
        REJETE
    }

    // ✅ Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }

    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }

    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}

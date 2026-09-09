package com.diallobank.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "virements")
public class Virement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId; // 🔹 Multi-tenant

    // ✅ Pour virement interne
    private String source;
    private String destinataire;

    // ✅ Pour virement externe
    private String beneficiaire;

    @Column(nullable = false)
    private Double montant;

    @Column(length = 255)
    private String motif;

    @Column(name = "date_virement", nullable = false)
    private LocalDateTime dateVirement = LocalDateTime.now();

    // ✅ Constructeur vide obligatoire pour JPA
    public Virement() {}

    // ✅ Constructeur pour virement interne
    public Virement(Long id, String source, String destinataire, Double montant, String motif, LocalDateTime dateVirement) {
        this.id = id;
        this.source = source;
        this.destinataire = destinataire;
        this.montant = montant;
        this.motif = motif;
        this.dateVirement = dateVirement;
    }

    // ✅ Constructeur pour virement externe
    public Virement(Long id, String source, String beneficiaire, Double montant, String motif, LocalDateTime dateVirement, boolean isExterne) {
        this.id = id;
        this.source = source;
        this.beneficiaire = beneficiaire;
        this.montant = montant;
        this.motif = motif;
        this.dateVirement = dateVirement;
    }

    // ✅ Constructeur avec userId (multi-tenant)
    public Virement(Long id, String source, String destinataireOuBeneficiaire, Double montant, String motif, LocalDateTime dateVirement, String userId) {
        this.id = id;
        this.source = source;
        this.destinataire = destinataireOuBeneficiaire;
        this.beneficiaire = destinataireOuBeneficiaire;
        this.montant = montant;
        this.motif = motif;
        this.dateVirement = dateVirement;
        this.userId = userId;
    }

    // ✅ Getters et setters
    public Long getId() { return id; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestinataire() { return destinataire; }
    public void setDestinataire(String destinataire) { this.destinataire = destinataire; }

    public String getBeneficiaire() { return beneficiaire; }
    public void setBeneficiaire(String beneficiaire) { this.beneficiaire = beneficiaire; }

    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public LocalDateTime getDateVirement() { return dateVirement; }
    public void setDateVirement(LocalDateTime dateVirement) { this.dateVirement = dateVirement; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}

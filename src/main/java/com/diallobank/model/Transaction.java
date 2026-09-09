package com.diallobank.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "name_transaction", length = 250)
    private String nameTransaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_transaction")
    private TypeTransaction typeTransaction;

    @Column(nullable = false)
    private Float montant;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // 🔹 Constructeur personnalisé utilisé dans BankingService
    public Transaction(Long id, String nameTransaction, TypeTransaction typeTransaction, Float montant, LocalDateTime createdAt) {
        this.id = id;
        this.nameTransaction = nameTransaction;
        this.typeTransaction = typeTransaction;
        this.montant = montant;
        this.createdAt = createdAt;
    }

    // 🔹 Constructeur avec userId (multi-tenant)
    public Transaction(Long id, String nameTransaction, TypeTransaction typeTransaction, Float montant, LocalDateTime createdAt, String userId) {
        this.id = id;
        this.nameTransaction = nameTransaction;
        this.typeTransaction = typeTransaction;
        this.montant = montant;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    // 🔹 Enum pour les types de transaction
    public enum TypeTransaction {
        Dépot,
        Paiement,
        Retrait
    }
}

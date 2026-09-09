package com.diallobank.repository;

import com.diallobank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    // Trouver toutes les transactions par type
    List<Transaction> findByTypeTransaction(Transaction.TypeTransaction typeTransaction);

    // Trouver toutes les transactions supérieures à un montant donné
    List<Transaction> findByMontantGreaterThan(Float montant);

    List<Transaction> findByUserId(String userId);

    // Trouver toutes les transactions créées après une date donnée
    List<Transaction> findByCreatedAtAfter(java.time.LocalDateTime date);
}

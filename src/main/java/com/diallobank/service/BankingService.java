package com.diallobank.service;

import com.diallobank.model.Compte;
import com.diallobank.model.Transaction;
import com.diallobank.model.Virement;
import com.diallobank.repository.CompteRepository;
import com.diallobank.repository.TransactionRepository;
import com.diallobank.repository.VirementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankingService {

    private final CompteRepository compteRepository;
    private final TransactionRepository transactionRepository;
    private final VirementRepository virementRepository;

    public BankingService(CompteRepository compteRepository,
                          TransactionRepository transactionRepository,
                          VirementRepository virementRepository) {
        this.compteRepository = compteRepository;
        this.transactionRepository = transactionRepository;
        this.virementRepository = virementRepository;
    }

    // ✅ Ajouter un compte (multi-tenant)
public Compte addCompte(Compte compte, String userId) {
    // Associer le compte à l'utilisateur connecté
    compte.setUserId(userId);

    // Vérification simple
    if (compte.getSolde() < 0) {
        throw new IllegalArgumentException("Le solde initial ne peut pas être négatif !");
    }

    // Sauvegarde du compte
    return compteRepository.save(compte);
}


    // 🔹 Récupérer les comptes selon le rôle
    public List<Compte> getComptes(String userId, String role) {
        if ("admin".equalsIgnoreCase(role)) {
            return compteRepository.findAll();
        } else {
            return compteRepository.findByUserId(userId);
        }
    }

    // 🔹 Récupérer les transactions selon le rôle
    public List<Transaction> getTransactions(String userId, String role) {
        if ("admin".equalsIgnoreCase(role)) {
            return transactionRepository.findAll();
        } else {
            return transactionRepository.findByUserId(userId);
        }
    }

    // 🔹 Récupérer les virements selon le rôle
    public List<Virement> getVirements(String userId, String role) {
        if ("admin".equalsIgnoreCase(role)) {
            return virementRepository.findAll();
        } else {
            return virementRepository.findByUserId(userId);
        }
    }

    // ✅ Virement interne (inchangé)
    public Virement effectuerVirementInterne(Long sourceId, Long destId, double montant, String motif) {
        Compte source = compteRepository.findById(sourceId).orElseThrow();
        Compte dest = compteRepository.findById(destId).orElseThrow();

        if (source.getSolde() < montant) {
            throw new IllegalArgumentException("Solde insuffisant !");
        }

        source.setSolde(source.getSolde() - montant);
        dest.setSolde(dest.getSolde() + montant);
        compteRepository.save(source);
        compteRepository.save(dest);

        Transaction retrait = new Transaction(null, "Virement vers " + dest.getNomCompte(),
                Transaction.TypeTransaction.Retrait, (float) montant, LocalDateTime.now());
        Transaction depot = new Transaction(null, "Virement reçu de " + source.getNomCompte(),
                Transaction.TypeTransaction.Dépot, (float) montant, LocalDateTime.now());
        transactionRepository.save(retrait);
        transactionRepository.save(depot);

        Virement virement = new Virement(null, source.getNomCompte(), dest.getNomCompte(), montant, motif, LocalDateTime.now());
        return virementRepository.save(virement);
    }

    // ✅ Virement externe (inchangé)
    public Virement effectuerVirementExterne(Long sourceId, String beneficiaire, double montant, String motif) {
        Compte source = compteRepository.findById(sourceId).orElseThrow();

        if (source.getSolde() < montant) {
            throw new IllegalArgumentException("Solde insuffisant !");
        }

        source.setSolde(source.getSolde() - montant);
        compteRepository.save(source);

        Transaction paiement = new Transaction(null, "Virement vers " + beneficiaire,
                Transaction.TypeTransaction.Paiement, (float) montant, LocalDateTime.now());
        transactionRepository.save(paiement);

        Virement virement = new Virement(null, source.getNomCompte(), beneficiaire, montant, motif, LocalDateTime.now());
        return virementRepository.save(virement);
    }

    // ✅ Calculs financiers (inchangé)
    public double calculerSoldeTotal() {
        return compteRepository.findAll().stream().mapToDouble(Compte::getSolde).sum();
    }

    public double calculerRevenusMois() {
        return transactionRepository.findAll().stream()
                .filter(t -> t.getTypeTransaction() == Transaction.TypeTransaction.Dépot)
                .mapToDouble(Transaction::getMontant)
                .sum();
    }

    public double calculerDepensesMois() {
        return transactionRepository.findAll().stream()
                .filter(t -> t.getTypeTransaction() == Transaction.TypeTransaction.Retrait
                        || t.getTypeTransaction() == Transaction.TypeTransaction.Paiement)
                .mapToDouble(Transaction::getMontant)
                .sum();
    }
}

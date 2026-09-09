package com.diallobank.controller;

import com.diallobank.model.Virement;
import com.diallobank.model.Transaction;
import com.diallobank.service.BankingService;
import com.diallobank.repository.VirementRepository;
import com.diallobank.repository.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/virements")
public class VirementController {

    private final BankingService bankingService;
    private final VirementRepository virementRepository;
    private final TransactionRepository transactionRepository;

    public VirementController(BankingService bankingService,
                              VirementRepository virementRepository,
                              TransactionRepository transactionRepository) {
        this.bankingService = bankingService;
        this.virementRepository = virementRepository;
        this.transactionRepository = transactionRepository;
    }

        @PostMapping("/interne")
    public Virement virementInterne(@RequestParam Long sourceId,
                                    @RequestParam Long destId,
                                    @RequestParam double montant,
                                    @RequestParam(required = false) String motif) {
        return bankingService.effectuerVirementInterne(sourceId, destId, montant, motif);
    }

    @PostMapping("/externe")
    public Virement virementExterne(@RequestParam Long sourceId,
                                    @RequestParam String beneficiaire,
                                    @RequestParam double montant,
                                    @RequestParam(required = false) String motif) {
        return bankingService.effectuerVirementExterne(sourceId, beneficiaire, montant, motif);
    }

     // ✅ GET : récupérer tous les virements
    @GetMapping
    public List<Virement> getVirements(@RequestParam String userId, @RequestParam String role) {
        return bankingService.getVirements(userId, role);
    }

    // ✅ GET : récupérer un virement par ID
    @GetMapping("/{id}")
    public Virement getVirementById(@PathVariable Long id) {
        return virementRepository.findById(id).orElse(null);
    }

    // ✅ POST : créer un nouveau virement ET une transaction associée
    @PostMapping
    public Virement createVirement(@RequestBody Virement virement) {
        // 1. Sauvegarder le virement
        Virement savedVirement = virementRepository.save(virement);

        // 2. Créer une transaction de type Dépôt
        Transaction transaction = new Transaction();
        transaction.setNameTransaction("Virement vers " + virement.getBeneficiaire());
        transaction.setTypeTransaction(Transaction.TypeTransaction.Dépot);
        transaction.setMontant(virement.getMontant().floatValue());
        transaction.setCreatedAt(LocalDateTime.now());

        // 3. Sauvegarder la transaction
        transactionRepository.save(transaction);

        return savedVirement;
    }
}

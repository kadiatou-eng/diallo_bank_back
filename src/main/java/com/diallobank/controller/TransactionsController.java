package com.diallobank.controller;

import com.diallobank.model.Transaction;
import com.diallobank.service.BankingService;
import org.springframework.web.bind.annotation.*;
// import org.springframework.http.ResponseEntity;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final BankingService bankingService; // ✅ injection du service

    public TransactionsController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    // ✅ GET : récupérer toutes les transactions (multi-tenant)
    @GetMapping
    public List<Transaction> getTransactions(@RequestParam String userId, @RequestParam String role) {
        return bankingService.getTransactions(userId, role);
    }

    // // ✅ POST : créer une transaction
    // @PostMapping
    // public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction, @RequestParam String userId) {
    //     Transaction saved = bankingService.addTransaction(transaction, userId);
    //     return ResponseEntity.ok(saved);
    // }

    // // ✅ DELETE : supprimer une transaction
    // @DeleteMapping("/{id}")
    // public void deleteTransaction(@PathVariable Long id, @RequestParam String userId, @RequestParam String role) {
    //     bankingService.deleteTransaction(id, userId, role);
    // }
}

package com.diallobank.controller;

import com.diallobank.model.Compte;
import com.diallobank.service.BankingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/comptes")
public class ComptesController {

    private final BankingService bankingService; // ✅ injection du service

    public ComptesController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    // ✅ GET : statistiques globales
    @GetMapping("/stats")
    public Map<String, Double> getStats() {
        Map<String, Double> stats = new HashMap<>();
        stats.put("soldeTotal", bankingService.calculerSoldeTotal());
        stats.put("revenus", bankingService.calculerRevenusMois());
        stats.put("depenses", bankingService.calculerDepensesMois());
        return stats;
    }

    // ✅ GET : récupérer tous les comptes (multi-tenant)
    @GetMapping
    public List<Compte> getComptes(@RequestParam String userId, @RequestParam String role) {
        return bankingService.getComptes(userId, role);
    }

    // ✅ GET : récupérer un seul compte par ID
    @GetMapping("/{id}")
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        Optional<Compte> compte = bankingService.getComptes(null, "admin") // ⚠️ simplifié
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return compte.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // ✅ POST : créer un nouveau compte
    @PostMapping
    public ResponseEntity<Compte> createCompte(@RequestBody Compte compte, @RequestParam String userId) {
        if (compte.getSolde() < 0) {
            return ResponseEntity.badRequest().build();
        }
        Compte saved = bankingService.addCompte(compte, userId);
        return ResponseEntity.ok(saved);
    }
}

package com.diallobank.controller;

import com.diallobank.model.Pret;
import com.diallobank.service.PretsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/prets")
public class PretsController {

    private final PretsService pretsService;

    public PretsController(PretsService pretsService) {
        this.pretsService = pretsService;
    }

    // ✅ GET : liste des prêts (multi-tenant)
    @GetMapping
    public List<Pret> getPrets(@RequestParam String userId, @RequestParam String role) {
        return pretsService.getPrets(userId, role);
    }

    // ✅ POST : créer un prêt
    @PostMapping
    public Pret createPret(@RequestBody Pret pret, @RequestParam String userId) {
        return pretsService.createPret(pret, userId);
    }

    // ✅ PATCH : mise à jour du statut
    @PatchMapping("/{id}")
    public Pret updateStatut(@PathVariable Long id,
                             @RequestParam Pret.Statut statut,
                             @RequestParam String userId,
                             @RequestParam String role) {
        return pretsService.updateStatut(id, statut, userId, role);
    }

    // ✅ DELETE : supprimer un prêt
    @DeleteMapping("/{id}")
    public void deletePret(@PathVariable Long id,
                           @RequestParam String userId,
                           @RequestParam String role) {
        pretsService.deletePret(id, userId, role);
    }
}

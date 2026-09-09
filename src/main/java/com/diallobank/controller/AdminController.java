package com.diallobank.controller;

import com.diallobank.model.Compte;
import com.diallobank.model.Pret;
import com.diallobank.repository.CompteRepository;
import com.diallobank.repository.PretRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CompteRepository compteRepository;
    private final PretRepository pretRepository;

    public AdminController(CompteRepository compteRepository, PretRepository pretRepository) {
        this.compteRepository = compteRepository;
        this.pretRepository = pretRepository;
    }

    // ✅ Endpoint combiné
    @GetMapping("/dashboard")
    public AdminDashboardResponse getDashboard() {
        List<Compte> comptes = compteRepository.findAll();
        List<Pret> prets = pretRepository.findAll();
        return new AdminDashboardResponse(comptes, prets);
    }
}

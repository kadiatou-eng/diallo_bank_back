package com.diallobank.service;

import com.diallobank.model.Pret;
import com.diallobank.repository.PretRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PretsService {

    private final PretRepository pretRepository;

    public PretsService(PretRepository pretRepository) {
        this.pretRepository = pretRepository;
    }

    public List<Pret> getPrets(String userId, String role) {
        if ("admin".equalsIgnoreCase(role)) {
            return pretRepository.findAll();
        } else {
            return pretRepository.findByUserId(userId);
        }
    }

    public Pret createPret(Pret pret, String userId) {
        pret.setUserId(userId);
        return pretRepository.save(pret);
    }

    public Pret updateStatut(Long id, Pret.Statut statut, String userId, String role) {
        Pret pret = pretRepository.findById(id).orElseThrow();
        if (!"admin".equalsIgnoreCase(role) && !pret.getUserId().equals(userId)) {
            throw new RuntimeException("Accès refusé");
        }
        pret.setStatut(statut);
        return pretRepository.save(pret);
    }

    public void deletePret(Long id, String userId, String role) {
        Pret pret = pretRepository.findById(id).orElseThrow();
        if (!"admin".equalsIgnoreCase(role) && !pret.getUserId().equals(userId)) {
            throw new RuntimeException("Accès refusé");
        }
        pretRepository.delete(pret);
    }
}

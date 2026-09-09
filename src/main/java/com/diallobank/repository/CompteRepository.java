package com.diallobank.repository;

import com.diallobank.model.Compte;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// ✅ Interface qui hérite de JpaRepository

public interface CompteRepository extends JpaRepository<Compte, Long> {
    List<Compte> findByUserId(String userId);
}

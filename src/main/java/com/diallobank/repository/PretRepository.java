package com.diallobank.repository;

import com.diallobank.model.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PretRepository extends JpaRepository<Pret, Long> {
    List<Pret> findByUserId(String userId);
}
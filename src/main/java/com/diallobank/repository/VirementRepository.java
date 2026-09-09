package com.diallobank.repository;

import com.diallobank.model.Virement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VirementRepository extends JpaRepository<Virement, Long> {
     List<Virement> findByUserId(String userId);
}

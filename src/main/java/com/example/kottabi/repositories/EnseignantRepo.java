package com.example.kottabi.repositories;

import com.example.kottabi.models.Enseignant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantRepo extends JpaRepository<Enseignant, Long> {
    Page<Enseignant> findBySpecialite(
            String specialite,
            Pageable pageable
    );
}

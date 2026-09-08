package com.example.kottabi.repositories;

import com.example.kottabi.models.Eleve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EleveRepo extends JpaRepository<Eleve, Long> {
    Page<Eleve> findAll(Pageable pageable);

    Optional<Eleve> findById(long id);
}

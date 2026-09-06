package com.example.kottabi.repositories;

import com.example.kottabi.models.Eleve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EleveRepo extends JpaRepository<Eleve, Long> {
    Page<Eleve> findAll(Pageable pageable);

    Eleve findById(long id);
}

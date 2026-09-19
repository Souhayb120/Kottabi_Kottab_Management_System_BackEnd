package com.example.kottabi.repositories;

import com.example.kottabi.models.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepo  extends JpaRepository<Participation, Long> {
    Page<Participation> findByEleveUsername(String eleveUsername, Pageable pageable);
    long count();

}

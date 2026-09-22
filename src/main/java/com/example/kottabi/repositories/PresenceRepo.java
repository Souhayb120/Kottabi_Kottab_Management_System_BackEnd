package com.example.kottabi.repositories;

import com.example.kottabi.enums.Statut;
import com.example.kottabi.models.Presence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PresenceRepo extends JpaRepository<Presence, Long> {

    Page<Presence> findByEleveUsername(String username, Pageable pageable);

    Page<Presence> findByDate(LocalDate date ,Pageable pageable);

    long countByStatut(Statut statut);

    Page<Presence> findByStatut(Statut statut , Pageable pageable);

}

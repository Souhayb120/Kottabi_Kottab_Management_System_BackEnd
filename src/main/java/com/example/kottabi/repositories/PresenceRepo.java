package com.example.kottabi.repositories;

import com.example.kottabi.models.Presence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresenceRepo extends JpaRepository<Presence, Long> {


    Page<Presence> findByEleveUsername(String username, Pageable pageable);
}

package com.example.kottabi.repositories;

import com.example.kottabi.models.Presence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresenceRepo extends JpaRepository<Presence, Long> {}

package com.example.kottabi.repositories;

import com.example.kottabi.models.Eleve;
import com.example.kottabi.models.Progression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressionRepo extends JpaRepository<Progression , Long> {

    Page<Progression> findByEleve(Eleve eleve);

    Page<Progression> findByEleveUsername(String username , Pageable pageable);
}

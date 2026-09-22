package com.example.kottabi.repositories;

import com.example.kottabi.models.Eleve;
import com.example.kottabi.models.Progression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProgressionRepo extends JpaRepository<Progression, Long> {
	long count();
	Page<Progression> findByEleveUsername(String username, Pageable pageable);
	Page<Progression> findTop6ByOrderByIdDesc(Pageable pageable);
	Page<Progression> findAll(Pageable pageable);

	Page<Progression> findProgressionsByEnseignant_Username(String enseignantUsername , Pageable pageable);


}

package com.example.kottabi.services;

import com.example.kottabi.DTO.EnseignantDTO.EnseignantRequestDTO;
import com.example.kottabi.DTO.EnseignantDTO.EnseignantResponseDTO;
import com.example.kottabi.models.Enseignant;
import org.springframework.data.domain.Page;

public interface EnseignantService {
    EnseignantResponseDTO ajouterEnseignant(EnseignantRequestDTO enseignantRequestDTO);
    Enseignant editEnseignant(long id , EnseignantRequestDTO enseignantRequestDTO);
    void supprimerEnseignant(long id);
    long countEnseignant();
    EnseignantResponseDTO consulterEnseignantById(long id);
    EnseignantResponseDTO consulterEnseignantByUsername(String username);
    Page<EnseignantResponseDTO> consulterEnseignantBySpecialite(String specialite,int page , int size);
    Page<EnseignantResponseDTO> consulterEnseignants(int page , int size);
}

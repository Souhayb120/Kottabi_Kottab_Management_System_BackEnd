package com.example.kottabi.services;

import com.example.kottabi.DTO.EleveRequestDTO;
import com.example.kottabi.DTO.EleveResponseDTO;
import com.example.kottabi.DTO.EnseignantRequestDTO;
import com.example.kottabi.DTO.EnseignantResponseDTO;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.models.Enseignant;
import org.springframework.data.domain.Page;

public interface EnseignantService {
    EnseignantResponseDTO ajouterEnseignant(EnseignantRequestDTO enseignantRequestDTO);
    Enseignant editEnseignant(long id , EnseignantRequestDTO enseignantRequestDTO);
    void supprimerEnseignant(long id);
    EnseignantResponseDTO consulterEnseignantById(long id);
    Page<EnseignantResponseDTO> consulterEnseignantBySpecialite(String specialite,int page , int size);
    Page<Enseignant> consulterEnseignants(int page , int size);
}

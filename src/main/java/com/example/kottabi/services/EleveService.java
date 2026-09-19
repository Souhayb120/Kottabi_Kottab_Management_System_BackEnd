package com.example.kottabi.services;


import com.example.kottabi.DTO.EleveDTO.EleveRequestDTO;
import com.example.kottabi.DTO.EleveDTO.EleveResponseDTO;
import com.example.kottabi.models.Eleve;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface EleveService {
    EleveResponseDTO ajouterEleve(EleveRequestDTO eleve);
    Eleve editEleve(long id , EleveRequestDTO eleve);
    void supprimerEleve(long id);
    EleveResponseDTO consulterEleveById(long id);
    EleveResponseDTO consulterEleveByUsername(String username);
    Page<EleveResponseDTO> consulterEleves(int page , int size);
    long countEleve();
}

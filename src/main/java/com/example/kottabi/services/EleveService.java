package com.example.kottabi.services;


import com.example.kottabi.DTO.EleveRequestDTO;
import com.example.kottabi.DTO.EleveResponseDTO;
import com.example.kottabi.models.Eleve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface EleveService {
    EleveResponseDTO ajouterEleve(EleveRequestDTO eleve);
    Eleve editEleve(long id , EleveRequestDTO eleve);
    void supprimerEleve(long id);
    EleveResponseDTO consulterEleveById(long id);
    Page<Eleve> consulterEleves(int page , int size);
}

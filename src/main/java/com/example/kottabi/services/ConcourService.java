package com.example.kottabi.services;


import com.example.kottabi.DTO.ConcourDTO.ConcourRequestDTO;
import com.example.kottabi.DTO.ConcourDTO.ConcourResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ConcourService {
    ConcourResponseDTO ajouterConcour(ConcourRequestDTO concourRequestDTO);
    ConcourResponseDTO editConcour(long id , ConcourRequestDTO concourRequestDTO);
    void supprimerConcour(long id);
    ConcourResponseDTO consulterConcourById(long id);
}

package com.example.kottabi.services;


import com.example.kottabi.DTO.ProgressionRequestDTO;
import com.example.kottabi.DTO.ProgressionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProgressionService {
    ProgressionResponseDTO ajouterProgression(ProgressionRequestDTO progressionRequestDTO);
    Page<ProgressionResponseDTO> consulterProgressions(int page , int size);
    void supprimerProgression(long id);
    ProgressionResponseDTO modifierProgressionById(long id, ProgressionRequestDTO progressionRequestDTO);
    Page<ProgressionResponseDTO> consulterProgressionByEleveUserName(String username , int page , int size);
}

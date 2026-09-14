package com.example.kottabi.services;

import com.example.kottabi.DTO.PresenceDTO.PresenceRequestDTO;
import com.example.kottabi.DTO.PresenceDTO.PresenceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PresenceService {
    PresenceResponseDTO enregistrerAbsenceEleve(PresenceRequestDTO presenceRequestDTO);
    void modifierAbsenceStatut(long id , String statut);
    void supprimerAbsence(long id);
    Page<PresenceResponseDTO> consulterLesAbsences(int page , int size);
}

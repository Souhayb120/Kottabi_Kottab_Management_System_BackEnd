package com.example.kottabi.services;

import com.example.kottabi.DTO.PresenceRequestDTO;
import com.example.kottabi.DTO.PresenceResponseDTO;
import com.example.kottabi.enums.Statut;
import com.example.kottabi.models.Presence;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PresenceService {
    PresenceResponseDTO enregistrerAbsenceEleve(PresenceRequestDTO presenceRequestDTO);
    void modifierAbsenceStatut(long id , String statut);
    void supprimerAbsence(long id);
    Page<Presence> consulterLesAbsences(int page , int size);
}

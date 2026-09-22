package com.example.kottabi.services;

import com.example.kottabi.DTO.PresenceDTO.PresenceRequestDTO;
import com.example.kottabi.DTO.PresenceDTO.PresenceResponseDTO;
import com.example.kottabi.models.Presence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface PresenceService {
    PresenceResponseDTO enregistrerAbsenceEleve(PresenceRequestDTO presenceRequestDTO);
    void modifierAbsenceStatut(long id , String statut);
    void supprimerAbsence(long id);
    Page<PresenceResponseDTO> consulterLesAbsences(int page , int size);
    Page<PresenceResponseDTO> consulterPresencesByEleve(String username , int page , int size);


    Page<PresenceResponseDTO> getCurrentDayPresence(int page , int size);
    long countByStatut(String statut);
    Page<PresenceResponseDTO> getPresenceByStatut(String statut , int page ,int size);

}

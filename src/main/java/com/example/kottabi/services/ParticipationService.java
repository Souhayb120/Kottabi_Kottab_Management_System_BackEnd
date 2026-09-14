package com.example.kottabi.services;

import com.example.kottabi.DTO.ParticipationDTO.ParticipationRequestDTO;
import com.example.kottabi.DTO.ParticipationDTO.ParticipationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ParticipationService {
    ParticipationResponseDTO registerParticipation(ParticipationRequestDTO participationRequestDTO);
    Page<ParticipationResponseDTO> findAll(int page , int size);
    void supprimerParticipation(long id);
}

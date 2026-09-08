package com.example.kottabi.services;

import com.example.kottabi.DTO.ParticipationRequestDTO;
import com.example.kottabi.DTO.ParticipationResponseDTO;
import com.example.kottabi.models.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ParticipationService {
    ParticipationResponseDTO registerParticipation(ParticipationRequestDTO participationRequestDTO);
    Page<Participation> findAll(int page , int size);
    void supprimerParticipation(long id);
}

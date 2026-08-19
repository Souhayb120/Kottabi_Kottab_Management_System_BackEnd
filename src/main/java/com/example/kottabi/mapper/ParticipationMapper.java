package com.example.kottabi.mapper;

import com.example.kottabi.DTO.ParticipationRequestDTO;
import com.example.kottabi.DTO.ParticipationResponseDTO;
import com.example.kottabi.models.Participation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipationMapper {
    Participation toEntity(ParticipationRequestDTO participationRequestDTO);
    ParticipationResponseDTO toDTO(Participation participation);
}

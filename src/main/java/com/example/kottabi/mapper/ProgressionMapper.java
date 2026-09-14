package com.example.kottabi.mapper;

import com.example.kottabi.DTO.AI_DTO.ParticipationAI;
import com.example.kottabi.DTO.AI_DTO.ProgressionAI;
import com.example.kottabi.DTO.ProgressionDTO.ProgressionRequestDTO;
import com.example.kottabi.DTO.ProgressionDTO.ProgressionResponseDTO;
import com.example.kottabi.models.Participation;
import com.example.kottabi.models.Progression;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgressionMapper {
    Progression toEntity(ProgressionRequestDTO progressionRequestDTO);
    ProgressionResponseDTO toDTO(Progression progression);
    ProgressionAI toProgressAi(Progression progression);

}

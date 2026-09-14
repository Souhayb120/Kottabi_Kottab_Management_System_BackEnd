package com.example.kottabi.mapper;

import com.example.kottabi.DTO.AI_DTO.AiRapportRequestDTO;
import com.example.kottabi.DTO.AI_DTO.AiRapportResponceDTO;
import com.example.kottabi.DTO.ConcourDTO.ConcourResponseDTO;
import com.example.kottabi.models.Concour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AiRapportMapper {
    AiRapportResponceDTO toDTO(AiRapportRequestDTO concour);

}

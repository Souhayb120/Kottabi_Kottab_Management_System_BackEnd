package com.example.kottabi.mapper;

import com.example.kottabi.DTO.ProgressionRequestDTO;
import com.example.kottabi.DTO.ProgressionResponseDTO;
import com.example.kottabi.models.Progression;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgressionMapper {
    Progression toEntity(ProgressionRequestDTO progressionRequestDTO);
    ProgressionResponseDTO toDTO(Progression progression);
}

package com.example.kottabi.mapper;

import com.example.kottabi.DTO.EleveRequestDTO;
import com.example.kottabi.DTO.EleveResponseDTO;
import com.example.kottabi.models.Eleve;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EleveMapper {
    Eleve toEntity(EleveRequestDTO eleveRequestDTO);
    EleveResponseDTO toDTO(Eleve eleve);
}

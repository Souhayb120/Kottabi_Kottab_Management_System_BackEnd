package com.example.kottabi.mapper;

import com.example.kottabi.DTO.EleveDTO.EleveRequestDTO;
import com.example.kottabi.DTO.EleveDTO.EleveResponseDTO;
import com.example.kottabi.models.Eleve;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EleveMapper {
    Eleve toEntity(EleveRequestDTO eleveRequestDTO);

    @Mapping(target = "id", source = "id")
    EleveResponseDTO toDTO(Eleve eleve);

}

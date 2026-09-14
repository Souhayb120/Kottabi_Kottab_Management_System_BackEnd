package com.example.kottabi.mapper;

import com.example.kottabi.DTO.EnseignantDTO.EnseignantRequestDTO;
import com.example.kottabi.DTO.EnseignantDTO.EnseignantResponseDTO;
import com.example.kottabi.models.Enseignant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnseignantMapper {
    Enseignant toEntity(EnseignantRequestDTO enseignantRequestDTO);
    EnseignantResponseDTO toDTO(Enseignant enseignant);
}

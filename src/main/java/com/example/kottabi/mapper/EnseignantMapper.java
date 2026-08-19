package com.example.kottabi.mapper;

import com.example.kottabi.DTO.EnseignantRequestDTO;
import com.example.kottabi.DTO.EnseignantResponseDTO;
import com.example.kottabi.models.Enseignant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnseignantMapper {
    Enseignant toEntity(EnseignantRequestDTO enseignantRequestDTO);
    EnseignantResponseDTO toDTO(Enseignant enseignant);
}

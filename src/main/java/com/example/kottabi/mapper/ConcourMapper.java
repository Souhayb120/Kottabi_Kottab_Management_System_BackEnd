package com.example.kottabi.mapper;

import com.example.kottabi.DTO.ConcourDTO.ConcourRequestDTO;
import com.example.kottabi.DTO.ConcourDTO.ConcourResponseDTO;
import com.example.kottabi.models.Concour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConcourMapper {
    Concour toEntity(ConcourRequestDTO concourRequestDTO);
    ConcourResponseDTO toDTO(Concour concour);
}

package com.example.kottabi.mapper;

import com.example.kottabi.DTO.ConcourRequestDTO;
import com.example.kottabi.DTO.ConcourResponseDTO;
import com.example.kottabi.models.Concour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConcourMapper {
    Concour toEntity(ConcourRequestDTO concourRequestDTO);
    ConcourResponseDTO toDTO(Concour concour);
}

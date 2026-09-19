package com.example.kottabi.mapper;

import com.example.kottabi.DTO.ConcourDTO.ConcourRequestDTO;
import com.example.kottabi.DTO.ConcourDTO.ConcourResponseDTO;
import com.example.kottabi.mapper.ParticipationMapper;
import com.example.kottabi.models.Concour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ParticipationMapper.class)
public interface ConcourMapper {
    Concour toEntity(ConcourRequestDTO concourRequestDTO);
    @Mapping(target = "id", source = "id")
    ConcourResponseDTO toDTO(Concour concour);
}

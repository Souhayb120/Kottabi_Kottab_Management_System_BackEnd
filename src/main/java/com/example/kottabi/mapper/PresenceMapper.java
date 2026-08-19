package com.example.kottabi.mapper;


import com.example.kottabi.DTO.PresenceRequestDTO;
import com.example.kottabi.DTO.PresenceResponseDTO;
import com.example.kottabi.models.Presence;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PresenceMapper {
    Presence toEntity(PresenceRequestDTO presenceRequestDTO);
    PresenceResponseDTO toDTO(Presence presence);
}

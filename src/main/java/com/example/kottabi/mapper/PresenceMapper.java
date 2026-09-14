package com.example.kottabi.mapper;


import com.example.kottabi.DTO.AI_DTO.PresenceAI;
import com.example.kottabi.DTO.PresenceDTO.PresenceRequestDTO;
import com.example.kottabi.DTO.PresenceDTO.PresenceResponseDTO;
import com.example.kottabi.models.Presence;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PresenceMapper {
    Presence toEntity(PresenceRequestDTO presenceRequestDTO);
    PresenceResponseDTO toDTO(Presence presence);
    PresenceAI toPresenceAi(Presence presence);
}

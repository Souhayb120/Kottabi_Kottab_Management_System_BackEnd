package com.example.kottabi.DTO.AI_DTO;

import com.example.kottabi.DTO.ParticipationDTO.ParticipationResponseDTO;
import com.example.kottabi.DTO.PresenceDTO.PresenceResponseDTO;
import com.example.kottabi.DTO.ProgressionDTO.ProgressionResponseDTO;
import com.example.kottabi.models.Participation;
import com.example.kottabi.models.Presence;
import com.example.kottabi.models.Progression;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AiRapportRequestDTO {
    @NotBlank(message = "Prenom is required")
    private String prenom;

    @NotBlank(message = "Nom is required")
    private String nom;

    @NotNull(message = "Date de naissance is required")
    private LocalDate dateNaissance;

    private List<PresenceAI> presences;
    private List<ProgressionAI> progressions;
    private List<ParticipationAI> participations;
}

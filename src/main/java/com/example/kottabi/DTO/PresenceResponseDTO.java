package com.example.kottabi.DTO;

import com.example.kottabi.enums.Statut;
import com.example.kottabi.models.Eleve;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PresenceResponseDTO {
    private LocalDate date;
    private Statut statut;
    private Eleve eleve;
}

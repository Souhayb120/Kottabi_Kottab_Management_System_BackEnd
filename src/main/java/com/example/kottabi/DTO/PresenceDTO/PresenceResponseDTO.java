package com.example.kottabi.DTO.PresenceDTO;

import com.example.kottabi.DTO.EleveDTO.EleveResponseDTO;
import com.example.kottabi.enums.Statut;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PresenceResponseDTO implements Serializable {
    private long id;
    private LocalDate date;
    private Statut statut;
    private EleveResponseDTO eleve;
}

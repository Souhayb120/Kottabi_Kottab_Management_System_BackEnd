package com.example.kottabi.DTO;

import com.example.kottabi.enums.Statut;
import com.example.kottabi.models.Eleve;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PresenceRequestDTO {
    private LocalDate date;
    private Statut statut;
    private long eleveId;
}

package com.example.kottabi.DTO.AI_DTO;

import com.example.kottabi.enums.Statut;
import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PresenceAI {
    private LocalDate date;


    private Statut statut;
}

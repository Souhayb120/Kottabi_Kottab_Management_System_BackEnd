package com.example.kottabi.DTO;

import com.example.kottabi.enums.NiveauHifz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcourRequestDTO {
    private String nom;
    private String description;
    private LocalDate dateCreation;
    private NiveauHifz niveauHifz;
}

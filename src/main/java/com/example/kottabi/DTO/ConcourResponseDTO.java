package com.example.kottabi.DTO;

import com.example.kottabi.enums.NiveauHifz;
import com.example.kottabi.models.Participation;
import jakarta.persistence.OneToMany;
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
public class ConcourResponseDTO {
    private String nom;
    private String description;
    private LocalDate dateCreation;
    private NiveauHifz niveauHifz;
    private List<Participation> participationList;
}

package com.example.kottabi.DTO.ConcourDTO;

import com.example.kottabi.enums.NiveauHifz;
import com.example.kottabi.models.Participation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcourResponseDTO implements Serializable {
    private long id;
    private String nom;
    private String description;
    private LocalDate dateCreation;
    private NiveauHifz niveauHifz;
    private List<Participation> participationList;
}
